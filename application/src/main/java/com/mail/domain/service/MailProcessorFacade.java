package com.mail.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mail.domain.entity.AbstractMail;

public interface MailProcessorFacade {
	CompletableFuture<Void> process(List<? extends AbstractMail> mails);
}
