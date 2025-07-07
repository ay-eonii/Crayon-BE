package com.mail.domain.entity;

import java.time.LocalDateTime;
import java.util.Map;

import com.mail.domain.entity.enums.CustomType;
import com.mail.domain.entity.enums.Status;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Mail extends AbstractMail {

	protected String id;
	protected String templateId;
	protected Map<CustomType, String> customData;
	protected String source;
	protected String destination;
	protected Status status;
	protected LocalDateTime scheduledTime;
	protected Long ttl;
	protected Long processId;

	// @Override
	// public String getUpdateExpression() {
	// 	return "";
	// }
	//
	// @Override
	// public Map<String, String> getExpressionAttributeNames() {
	// 	return Map.of();
	// }
	//
	// @Override
	// public Map<String, Object> getExpressionValues(MailTransformDto dto) {
	// 	return Map.of();
	// }

	@Override
	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	// @Override
	// public void setScheduledTime(LocalDateTime scheduledTime) {
	// 	this.scheduledTime = scheduledTime;
	// }

	@Override
	public AbstractMail updateScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
		return this;
	}

	@Override
	public AbstractMail cancel() {
		this.status = Status.CANCELED;
		return this;
	}
}
