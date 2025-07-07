package com.landing.application.usecase;

import java.io.IOException;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.domain.entity.Club;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubUpdateService;
import com.club.domain.service.ClubValidateService;
import com.club.exception.DuplicatedSubDomainException;
import com.landing.application.dto.request.CreateSubDomainRequest;
import com.landing.application.dto.request.LandingRequestDTO;
import com.landing.application.dto.request.LandingRequestDTO.General;
import com.landing.application.dto.response.LandingResponseDTO;
import com.landing.application.mapper.LandingMapper;
import com.landing.application.usecase.dto.LandingCreateEvent;
import com.landing.application.usecase.dto.LandingDeleteEvent;
import com.landing.domain.constant.ReservedSubDomain;
import com.landing.domain.entity.Landing;
import com.landing.domain.service.LandingGetService;
import com.landing.domain.service.LandingRouteService;
import com.landing.domain.service.LandingSaveService;
import com.landing.domain.service.LandingUpdateService;
import com.user.domain.entity.User;
import com.global.common.util.SubdomainFormatter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingGeneralManageUsecase {

	private final ClubGetService clubGetService;
	private final ClubUpdateService clubUpdateService;
	private final LandingGetService landingGetService;
	private final LandingMapper landingMapper;
	private final LandingUpdateService landingUpdateService;
	private final ClubValidateService clubValidateService;
	private final LandingSaveService landingSaveService;
	private final ApplicationEventPublisher landingEventPublisher;
	private final LandingRouteService landingRouteService;

	@Transactional(readOnly = true)
	public LandingResponseDTO.General readGeneral(String clubId, User user) {
		Club club = clubGetService.find(clubId);
		clubValidateService.checkAuthority(club.getId(), user);

		Landing landing = landingGetService.find(club);
		return landingMapper.toGeneralResponse(club, landing);
	}

	@Transactional
	public void update(General dto, User user) throws IOException {
		Club club = clubGetService.find(dto.clubId());
		clubValidateService.checkOwnerAuthority(club.getId(), user);

		updateSubdomainIfChanged(dto, club);
		Landing landing = landingGetService.find(club);
		landingUpdateService.update(landing, club, dto);
	}

	private void updateSubdomainIfChanged(General dto, Club club) {
		if (isSubDomainChanged(dto, club)) {
			String subdomain = SubdomainFormatter.formatSubdomain(dto.subDomain());
			String oldDomain = club.getSubDomain();

			checkReservedSubdomain(subdomain);
			clubValidateService.checkDuplicatedSubDomain(subdomain);
			landingRouteService.checkDuplication(subdomain);

			landingEventPublisher.publishEvent(new LandingCreateEvent(subdomain));
			landingEventPublisher.publishEvent(new LandingDeleteEvent(SubdomainFormatter.formatSubdomain(oldDomain)));
		}
	}

	private boolean isSubDomainChanged(General dto, Club club) {
		return !dto.subDomain().equals(club.getSubDomain());
	}

	@Transactional
	public void update(LandingRequestDTO.NotionSave dto, User user) {
		Club club = clubGetService.find(dto.clubId());
		clubValidateService.checkOwnerAuthority(club.getId(), user);
		clubUpdateService.update(club, dto.notionPageLink());
	}

	@Transactional
	public void create(User user, UUID clubId, CreateSubDomainRequest request) {
		Club club = clubValidateService.checkOwnerAuthority(clubId, user);
		String subdomain = SubdomainFormatter.formatSubdomain(request.subDomain());

		checkReservedSubdomain(subdomain);
		clubValidateService.checkDuplicatedSubDomain(subdomain);
		landingRouteService.checkDuplication(subdomain);

		landingEventPublisher.publishEvent(new LandingCreateEvent(subdomain));

		club.addSubDomain(request.subDomain());
		Landing landing = landingSaveService.save(club);
		club.addLanding(landing);
	}

	private void checkReservedSubdomain(String subDomain) {
		if (ReservedSubDomain.contains(subDomain)) {
			throw new DuplicatedSubDomainException();
		}
	}
}
