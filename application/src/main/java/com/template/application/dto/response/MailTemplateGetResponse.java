package com.template.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.template.domain.entity.MailTemplate;
import com.template.domain.service.dto.MailTemplateResponse;

import lombok.Builder;

@Builder
public record MailTemplateGetResponse(
	UUID templateId,
	String customTemplateName,
	String subject,
	String htmlPart,
	String textPart,
	LocalDateTime createdAt
) {
	public static MailTemplateGetResponse of(MailTemplate mailTemplate, MailTemplateResponse response) {
		return MailTemplateGetResponse.builder()
			.templateId(mailTemplate.getId())
			.customTemplateName(mailTemplate.getCustomTemplateName())
			.subject(response.subject())
			.htmlPart(response.htmlPart())
			.textPart(response.textPart())
			.createdAt(mailTemplate.getCreatedAt())
			.build();
	}

	public static MailTemplateGetResponse toResponse(MailTemplate mailTemplate, MailTemplateUpdateRequest request) {
		return MailTemplateGetResponse.builder()
			.templateId(mailTemplate.getId())
			.customTemplateName(mailTemplate.getCustomTemplateName())
			.subject(request.subject())
			.htmlPart(request.htmlPart())
			.textPart(request.textPart())
			.createdAt(LocalDateTime.now())
			.build();
	}
}
