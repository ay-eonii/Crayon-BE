package com.mail.domain.service;

import java.time.LocalDateTime;

import com.mail.domain.entity.Mail;

public record MailTransformDto(
	Mail mail,
	LocalDateTime scheduledTime
) {
	public static MailTransformDto of(Mail mail, LocalDateTime scheduledTime) {
		return new MailTransformDto(mail, scheduledTime);
	}
}
