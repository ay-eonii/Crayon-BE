package com.landing.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.domain.entity.Club;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubValidateService;
import com.landing.application.dto.response.LandingResponseDTO.All;
import com.landing.domain.entity.Landing;
import com.landing.domain.service.NotionGetService;
import com.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingAllSettingManageUsecase {

	private final ClubGetService clubGetService;
	private final NotionGetService notionGetService;
	private final ClubValidateService clubValidateService;

	public All readAll(String subDomain) {
		Club club = clubGetService.findBySubDomain(subDomain);
		Landing landing = club.getLanding();
		String parsedNotionPageLink = notionGetService.parseNotionPageLink(club.getNotionPageLink());

		return All.toAll(landing, parsedNotionPageLink);
	}

	@Transactional(readOnly = true)
	public boolean check(UUID clubId, User user) {
		Club club = clubGetService.find(clubId);
		clubValidateService.checkAuthority(club.getId(), user);

		return club.checkExistsSubDomain();
	}
}
