package com.template.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.club.domain.entity.Club;
import com.template.application.dto.request.MailTemplateSaveRequest;
import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.template.application.dto.response.MailTemplateGetResponse;
import com.template.domain.entity.MailTemplate;
import com.template.domain.repository.MailTemplateRepository;
import com.template.exception.TemplateNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailTemplateService {

	private final MailTemplateRepository mailTemplateRepository;
	private final MailTemplateManager mailTemplateManager;

	public Page<MailTemplate> findAll(String clubId, Pageable pageable) {
		UUID clubUUID = UUID.fromString(clubId);
		return mailTemplateRepository.findAllByClubId(clubUUID, pageable);
	}

	public MailTemplateGetResponse findWithSes(UUID templateId) {
		MailTemplate template = mailTemplateRepository.findById(templateId)
			.orElseThrow(TemplateNotFoundException::new);
		return mailTemplateManager.findFromSes(template);
	}

	public MailTemplate findFromLocal(UUID templateId) {
		return mailTemplateRepository.findById(templateId)
			.orElseThrow(TemplateNotFoundException::new);
	}

	public void save(MailTemplateSaveRequest dto, Club club) {
		MailTemplate template = dto.toMailTemplate(club);
		UUID templateId = mailTemplateRepository.save(template).getId();

		mailTemplateManager.uploadTemplate(dto, templateId);
	}

	public UUID uploadTemplate(MailTemplateSaveRequest dto) {
		UUID templateId = UUID.randomUUID();
		mailTemplateManager.uploadTemplate(dto, templateId);
		return templateId;
	}

	public void update(MailTemplateUpdateRequest dto, MailTemplate mailTemplate, UUID templateId) {
		mailTemplate.update(dto);
		mailTemplateManager.updateTemplate(dto, templateId);
	}

	public void delete(MailTemplate mailTemplate) {
		mailTemplateRepository.deleteById(mailTemplate.getId());
		mailTemplateManager.deleteTemplate(mailTemplate);
	}
}
