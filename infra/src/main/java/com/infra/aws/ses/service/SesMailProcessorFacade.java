package com.infra.aws.ses.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.infra.aws.ses.SesMail;
import com.mail.domain.entity.AbstractMail;
import com.mail.domain.service.MailProcessorFacade;
import com.mail.domain.service.MailSaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesMailProcessorFacade implements MailProcessorFacade {

	private final MailSaveService<SesMail> sesMailProcessor;

	@Override
	public CompletableFuture<Void> process(List<? extends AbstractMail> mails) {
		List<SesMail> sesMails = mails.stream()
			.map(mail -> SesMail.builder()
				.id(mail.getId())
				.templateId(mail.getTemplateId())
				.customData(mail.getCustomData())
				.source(mail.getSource())
				.destination(mail.getDestination())
				.status(mail.getStatus())
				.scheduledTime(mail.getScheduledTime())
				.ttl(mail.getTtl())
				.processId(mail.getProcessId())
				.build()
			)
			.toList();

		return sesMailProcessor.upload(sesMails);
	}
}
