package com.template.application.usecase;

import java.util.UUID;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.domain.entity.Club;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubManagerAuthService;
import com.template.application.dto.request.MailTemplateSaveRequest;
import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.template.application.dto.response.MailTemplateGetResponse;
import com.template.application.dto.response.MailTemplateListResponse;
import com.template.domain.entity.MailTemplate;
import com.template.domain.service.MailTemplateService;
import com.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailTemplateManageUseCase {

	private final ClubGetService clubGetService;
	private final MailTemplateService mailTemplateService;
	private final ClubManagerAuthService clubManagerAuthService;

	@Transactional
	public void save(MailTemplateSaveRequest dto, UUID clubId, User user) {
		Club club = checkAuthorityByClub(clubId.toString(), user);

		mailTemplateService.save(dto, club);
	}

	public Page<MailTemplateListResponse> findAll(String clubId, Pageable pageable) {
		clubGetService.find(clubId);

		return mailTemplateService.findAll(clubId, pageable)
			.map(MailTemplateListResponse::of);
	}

	@Cacheable(value = "mailTemplate", key = "#templateId")
	public MailTemplateGetResponse find(UUID templateId) {
		return mailTemplateService.findWithSes(templateId);
	}

	@Transactional
	@CachePut(value = "mailTemplate", key = "#templateId")
	public MailTemplateGetResponse update(MailTemplateUpdateRequest dto, UUID templateId, User user) {
		MailTemplate mailTemplate = checkAuthorityByMailTemplate(templateId, user);

		mailTemplateService.update(dto, mailTemplate, templateId);
		return MailTemplateGetResponse.toResponse(mailTemplate, dto);
	}

	@Transactional
	public void delete(UUID templateId, User user) {
		MailTemplate mailTemplate = checkAuthorityByMailTemplate(templateId, user);

		mailTemplateService.delete(mailTemplate);
	}

	private Club checkAuthorityByClub(String clubId, User manager) {
		Club club = clubGetService.find(clubId);
		clubManagerAuthService.checkAuthorization(club, manager);

		return club;
	}

	private MailTemplate checkAuthorityByMailTemplate(UUID templateId, User manager) {
		MailTemplate mailTemplate = mailTemplateService.findFromLocal(templateId);
		clubManagerAuthService.checkAuthorization(mailTemplate.getClub(), manager);

		return mailTemplate;
	}
}
