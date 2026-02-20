package com.mail.domain.service;

import java.util.UUID;

public interface MailLimiter {
	boolean tryConsume(UUID clubId, int requestSize);
}
