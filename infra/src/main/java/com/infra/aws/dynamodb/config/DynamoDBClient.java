package com.infra.aws.dynamodb.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.stereotype.Repository;

import com.infra.aws.dynamodb.builder.ScanRequestBuilder;
import com.infra.aws.ses.SesMail;
import com.mail.domain.service.MailUpdateClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactUpdateItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DynamoDBClient implements MailUpdateClient {

	private static final int BATCH_SIZE = 50;

	private final DynamoDbAsyncTable<SesMail> mailTable;
	private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

	@Override
	public CompletableFuture<Void> processMailOperation(long processId) {
		ScanEnhancedRequest scanRequest = ScanRequestBuilder.buildScanRequest(processId);

		return mailTable.scan(scanRequest).items()
			.map(SesMail::cancel)
			.buffer(BATCH_SIZE)
			.subscribe(this::executeBatchUpdate)
			.thenRun(() -> log.info("[MailUpdateService] 메일 예약 취소 작업 완료"))
			.exceptionally(e -> {
				log.error("[MailUpdateService] 메일 예약 취소 중 예외 발생", e);
				throw new CompletionException(e);
			});
	}

	@Override
	public CompletableFuture<Void> processMailOperation(long processId, LocalDateTime time) {
		ScanEnhancedRequest scanRequest = ScanRequestBuilder.buildScanRequest(processId);

		return mailTable.scan(scanRequest).items()
			.map(sesMail -> sesMail.updateScheduledTime(time))
			.buffer(BATCH_SIZE)
			.subscribe(this::executeBatchUpdate)
			.thenRun(() -> log.info("[MailUpdateService] 메일 예약 수정 작업 완료"))
			.exceptionally(e -> {
				log.error("[MailUpdateService] 메일 예약 수정 중 예외 발생", e);
				throw new CompletionException(e);
			});
	}

	private void executeBatchUpdate(List<SesMail> mails) {
		TransactWriteItemsEnhancedRequest transactWriteRequest = buildTransactRequest(mails);

		dynamoDbEnhancedAsyncClient.transactWriteItems(transactWriteRequest)
			.thenAccept(unused -> log.info("[MailUpdateService] 트랜잭션 수정/취소 성공 (배치 크기: {})", mails.size()))
			.exceptionally(e -> {
				log.error("[MailUpdateService] 트랜잭션 중 예외 발생", e);
				throw new CompletionException(e);
			});
	}

	private TransactWriteItemsEnhancedRequest buildTransactRequest(List<SesMail> mails) {
		TransactWriteItemsEnhancedRequest.Builder requestBuilder = TransactWriteItemsEnhancedRequest.builder();

		mails.forEach(mail -> {
			TransactUpdateItemEnhancedRequest<SesMail> updateRequest = TransactUpdateItemEnhancedRequest
				.builder(SesMail.class)
				.item(mail)
				.build();

			requestBuilder.addUpdateItem(mailTable, updateRequest);
		});

		return requestBuilder.build();
	}
}

