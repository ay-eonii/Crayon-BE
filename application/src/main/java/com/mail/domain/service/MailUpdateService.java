package com.mail.domain.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.mail.application.dto.request.MailUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailUpdateService {

	private final MailUpdateClient mailUpdateClient;

	public CompletableFuture<Void> updateScheduledTime(long processId, MailUpdateRequest dto) {
		return mailUpdateClient.processMailOperation(processId, dto.scheduledTime());
	}

	public CompletableFuture<Void> cancelMail(long processId) {
		return mailUpdateClient.processMailOperation(processId);
	}
}

