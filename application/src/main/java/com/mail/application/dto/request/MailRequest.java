package com.mail.application.dto.request;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.mail.domain.entity.CommonData;
import com.mail.domain.entity.MailInfo;
import com.mail.domain.entity.enums.Status;
import com.template.application.dto.request.MailTemplateSaveRequest;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record MailRequest(
	@NotNull Long processId,
	@NotNull LocalDateTime scheduledTime,
	@NotNull MailTemplateSaveRequest passTemplate,
	@NotNull MailTemplateSaveRequest failTemplate
) {

	public MailInfo toMailInfo(UUID passTemplateId, UUID failTemplateId, CommonData commonData) {
		// UTC로 변환 후 1시간 추가
		long ttl = scheduledTime.minusHours(9).plusDays(1)
			.toInstant(ZoneOffset.UTC)
			.getEpochSecond();

		return MailInfo.builder()
			.scheduledTime(scheduledTime)
			.status(Status.SCHEDULED)
			.ttl(ttl)
			.processId(processId)
			.passTemplateId(passTemplateId)
			.failTemplateId(failTemplateId)
			.commonData(commonData)
			.build();
	}
}
