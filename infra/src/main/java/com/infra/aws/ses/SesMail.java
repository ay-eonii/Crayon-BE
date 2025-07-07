package com.infra.aws.ses;

import java.time.LocalDateTime;
import java.util.Map;

import com.infra.aws.dynamodb.config.EnumMapAttributeConverter;
import com.infra.aws.dynamodb.config.LocalDateTimeConverter;
import com.mail.domain.entity.AbstractMail;
import com.mail.domain.entity.enums.CustomType;
import com.mail.domain.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SesMail extends AbstractMail {

	protected String id;
	protected String templateId;
	protected Map<CustomType, String> customData;
	protected String source;
	protected String destination;
	protected Status status;
	protected LocalDateTime scheduledTime;
	protected Long ttl;
	protected Long processId;

	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return id;
	}

	public SesMail updateScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
		return this;
	}

	public SesMail cancel() {
		this.status = Status.CANCELED;
		return this;
	}

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

	@DynamoDbConvertedBy(LocalDateTimeConverter.class)
	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	// @Override
	// public void setScheduledTime(LocalDateTime scheduledTime) {
	// 	this.scheduledTime = scheduledTime;
	// }

	@DynamoDbConvertedBy(EnumMapAttributeConverter.class)
	public Map<CustomType, String> getCustomData() {
		return customData;
	}
}
