package com.mail.domain.service;

public interface MailVerifyService {
	void sendVerifyCode(String email, String code);
}
