package com.mail.domain.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.application.domain.repository.dto.ApplicationWithStatus;
import com.mail.domain.entity.enums.CustomType;
import com.mail.domain.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mails {

	private final List<Mail> mails;

	public static Mails create(List<ApplicationWithStatus> applications, MailInfo mailInfo) {
		List<Mail> mails = applications.stream()
			.map(CustomData::of)
			.map(customData -> toMail(customData, mailInfo))
			.toList();

		return new Mails(mails);
	}

	public static Mail toMail(CustomData customData, MailInfo mailInfo) {
		Map<CustomType, String> data = new HashMap<>();
		data.putAll(mailInfo.getCommonData().getData());
		data.putAll(customData.getData());

		UUID templateId = customData.isPass() ? mailInfo.getPassTemplateId() : mailInfo.getFailTemplateId();

		return Mail.builder()
			.id(UUID.randomUUID().toString())
			.templateId(templateId.toString())
			.customData(data)
			.destination(customData.getEmail())
			.scheduledTime(mailInfo.getScheduledTime())
			.status(Status.SCHEDULED)
			.ttl(mailInfo.getTtl())
			.processId(mailInfo.getProcessId())
			.source(mailInfo.getMailSourceAddress())
			.build();
	}

	public int count() {
		return mails.size();
	}
}
