package com.mail.application.usecase;

import java.util.concurrent.CompletableFuture;

public interface MailSender {
	CompletableFuture<Void> send();
}
