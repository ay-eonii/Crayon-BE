package com.application.application.usecase;

import org.springframework.stereotype.Service;

import com.application.application.dto.request.ApplicationVerificationRequestDto;
import com.global.config.verify.VerificationService;
import com.mail.domain.service.MailVerifyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUseCase {

	private final VerificationService verificationService;
	private final MailVerifyService mailVerifyService;

	public void generate(String email) {
		String code = verificationService.generateCode(email);
		mailVerifyService.sendVerifyCode(email, code);
	}

	public void verify(ApplicationVerificationRequestDto.VerificationRequest dto) {
		verificationService.verifyCode(dto);
	}
}
