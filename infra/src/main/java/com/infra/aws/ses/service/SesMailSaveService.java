package com.infra.aws.ses.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.stereotype.Service;

import com.infra.aws.ses.SesMail;
import com.mail.domain.service.MailSaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesMailSaveService implements MailSaveService<SesMail> {

	private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
	private final DynamoDbAsyncTable<SesMail> mailTable;

	@Override
	public CompletableFuture<Void> upload(List<SesMail> mails) {
		TransactWriteItemsEnhancedRequest.Builder requestBuilder = TransactWriteItemsEnhancedRequest.builder();

		mails.forEach(mail -> requestBuilder.addUpdateItem(mailTable, mail));

		TransactWriteItemsEnhancedRequest request = requestBuilder.build();

		return dynamoDbEnhancedAsyncClient.transactWriteItems(request)
			.thenAccept(result -> log.info("[MailSaveService] | {} 개의 메일 트랜잭션 업로드 성공", mails.size()))
			.exceptionally(ex -> {
				log.error("[MailSaveService] | 배치 업로드 중 예외 발생: {}", ex.getMessage(), ex);
				throw new CompletionException(ex);
			});
	}

	/*
	 * 메일 단건 조작 관련 메서드
	 * 차후 사용할 수도 있을 것 같아 냅둠
	 */
	public void save(SesMail mail) {
		try {
			mailTable.putItem(mail);
			log.info("Saved mail with ID: {}", mail.getId());
		} catch (DynamoDbException e) {
			log.error("Failed to save mail ID {}: {}", mail.getId(), e.getMessage());
		}
	}

	public void update(SesMail mail) {
		try {
			mailTable.putItem(mail);
			log.info("Updated mail with ID: {}", mail.getId());
		} catch (DynamoDbException e) {
			log.error("Failed to update mail ID {}: {}", mail.getId(), e.getMessage());
		}
	}

	public void delete(SesMail mail) {
		try {
			mailTable.deleteItem(mail);
			log.info("Deleted mail with ID: {}", mail.getId());
		} catch (DynamoDbException e) {
			log.error("Failed to delete mail ID {}: {}", mail.getId(), e.getMessage());
		}
	}

	public CompletableFuture<SesMail> findById(String id) { // ID를 String으로 수정
		try {
			return mailTable.getItem(Key.builder().partitionValue(id).build());
		} catch (DynamoDbException e) {
			log.error("Failed to find mail ID {}: {}", id, e.getMessage());
			return null;
		}
	}
}
