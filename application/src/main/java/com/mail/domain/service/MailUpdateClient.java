package com.mail.domain.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public interface MailUpdateClient {

	CompletableFuture<Void> processMailOperation(long processId);

	CompletableFuture<Void> processMailOperation(long processId, LocalDateTime time);
}
