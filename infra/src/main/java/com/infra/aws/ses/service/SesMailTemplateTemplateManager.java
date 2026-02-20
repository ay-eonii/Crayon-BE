package com.infra.aws.ses.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infra.support.HtmlSanitizer;
import com.template.application.dto.request.MailTemplateSaveRequest;
import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.template.application.dto.response.MailTemplateGetResponse;
import com.template.domain.entity.MailTemplate;
import com.template.domain.service.MailTemplateManager;
import com.template.domain.service.dto.MailTemplateResponse;
import com.template.exception.SesTemplateException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.CreateTemplateRequest;
import software.amazon.awssdk.services.ses.model.DeleteTemplateRequest;
import software.amazon.awssdk.services.ses.model.GetTemplateRequest;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.ses.model.Template;
import software.amazon.awssdk.services.ses.model.UpdateTemplateRequest;

@Service
@RequiredArgsConstructor
public class SesMailTemplateTemplateManager implements MailTemplateManager {

	private final SesClient sesClient;
	private final HtmlSanitizer htmlSanitizer;

	@Override
	public void deleteTemplate(MailTemplate mailTemplate) {
		DeleteTemplateRequest deleteTemplateRequest = DeleteTemplateRequest.builder()
			.templateName(mailTemplate.getId().toString())
			.build();

		try {
			sesClient.deleteTemplate(deleteTemplateRequest);
		} catch (SesException e) {
			throw new SesTemplateException(e.getMessage());
		}
	}

	@Override
	public MailTemplateGetResponse findFromSes(MailTemplate mailTemplate) {
		UUID templateId = mailTemplate.getId();

		GetTemplateRequest getRequest = GetTemplateRequest.builder()
			.templateName(templateId.toString())
			.build();

		try {
			Template template = sesClient.getTemplate(getRequest).template();
			MailTemplateResponse mailTemplateResponse = new MailTemplateResponse(template.subjectPart(),
				template.htmlPart(), template.textPart());
			return MailTemplateGetResponse.of(mailTemplate, mailTemplateResponse);
		} catch (SesException e) {
			throw new SesTemplateException(e.getMessage());
		}
	}

	@Override
	public UUID uploadTemplate(MailTemplateSaveRequest dto, UUID templateId) {
		CreateTemplateRequest request = buildRequest(dto, templateId);

		try {
			sesClient.createTemplate(request);
		} catch (SesException e) {
			throw new SesTemplateException(e.getMessage());
		}
		return templateId;
	}

	@Override
	public void updateTemplate(MailTemplateUpdateRequest dto, UUID templateId) {
		String sanitizedHtmlPart = htmlSanitizer.sanitize(dto.htmlPart());

		Template template = Template.builder()
			.templateName(templateId.toString())
			.subjectPart(dto.subject())
			.textPart(dto.textPart())
			.htmlPart(sanitizedHtmlPart)
			.build();

		UpdateTemplateRequest updateRequest = UpdateTemplateRequest.builder()
			.template(template)
			.build();

		try {
			sesClient.updateTemplate(updateRequest);
		} catch (SesException e) {
			throw new SesTemplateException(e.getMessage());
		}
	}

	private CreateTemplateRequest buildRequest(MailTemplateSaveRequest dto, UUID templateId) {
		String sanitizedHtmlPart = htmlSanitizer.sanitize(dto.htmlPart());

		Template template = Template.builder()
			.templateName(templateId.toString())
			.subjectPart(dto.subject())
			.textPart(dto.textPart())
			.htmlPart(sanitizedHtmlPart)
			.build();

		return CreateTemplateRequest.builder()
			.template(template)
			.build();
	}
}
