package com.template.domain.service;

import java.util.UUID;

import com.template.application.dto.request.MailTemplateSaveRequest;
import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.template.application.dto.response.MailTemplateGetResponse;
import com.template.domain.entity.MailTemplate;

public interface MailTemplateManager {

	void deleteTemplate(MailTemplate mailTemplate);

	MailTemplateGetResponse findFromSes(MailTemplate mailTemplate);

	UUID uploadTemplate(MailTemplateSaveRequest dto, UUID templateId);

	void updateTemplate(MailTemplateUpdateRequest dto, UUID templateId);
}

