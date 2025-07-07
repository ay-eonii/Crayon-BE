package com.infra.aws.ses.service;

import org.springframework.stereotype.Service;

import com.mail.domain.service.MailVerifyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesMailVerifyService implements MailVerifyService {

	private static final String TEMPLATE_NAME = "VerifyMailTemplate";

	private final SesClient sesClient;

	public void sendVerifyCode(String email, String code) {
		String templateData = "{\"CODE\":\"" + code + "\"}";

		SendTemplatedEmailRequest emailRequest = SendTemplatedEmailRequest.builder()
			.destination(Destination.builder().toAddresses(email).build())
			.template(TEMPLATE_NAME)
			.templateData(templateData)
			.source("ewgt1234@naver.com") // SES에 검증된 이메일 주소
			.build();

		sesClient.sendTemplatedEmail(emailRequest);
		log.info("템플릿을 사용한 이메일 발송 성공: {}", email);
	}
}
