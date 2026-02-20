package com.mail.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mail.domain.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MailInfo {
	private final String mailSourceAddress = "mail@crayon.land";
	private final LocalDateTime scheduledTime;
	private final Status status;
	private final long ttl;
	private final long processId;
	private final UUID passTemplateId;
	private final UUID failTemplateId;
	private final CommonData commonData;
}
