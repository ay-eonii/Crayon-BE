package com.mail.domain.entity;

import java.time.LocalDateTime;
import java.util.Map;

import com.mail.domain.entity.enums.CustomType;
import com.mail.domain.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMail {

	protected String id;
	protected String templateId;
	protected Map<CustomType, String> customData;
	protected String source;
	protected String destination;
	protected Status status;
	protected LocalDateTime scheduledTime;
	protected Long ttl;
	protected Long processId;

	// public abstract String getUpdateExpression();

	// public abstract Map<String, String> getExpressionAttributeNames();

	// public abstract Map<String, Object> getExpressionValues(MailTransformDto dto);

	public abstract LocalDateTime getScheduledTime();

	// public abstract void setScheduledTime(LocalDateTime scheduledTime);

	public abstract AbstractMail updateScheduledTime(LocalDateTime scheduledTime);

	public abstract AbstractMail cancel();
}
