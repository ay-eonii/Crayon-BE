package com.mail.domain.service;

import com.mail.application.dto.request.MailRequest;

public interface MailReserveService {
	void create(MailRequest dto);
}
