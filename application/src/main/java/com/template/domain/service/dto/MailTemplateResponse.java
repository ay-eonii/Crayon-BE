package com.template.domain.service.dto;

public record MailTemplateResponse(
	String subject,
	String htmlPart,
	String textPart
) {
}

