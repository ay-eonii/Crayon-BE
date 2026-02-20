package com.mail.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mail.domain.entity.AbstractMail;

public interface MailSaveService<T extends AbstractMail> {
	CompletableFuture<Void> upload(List<T> mails);

	void save(T mail);

	void update(T mail);

	void delete(T mail);

	CompletableFuture<T> findById(String id);
}
