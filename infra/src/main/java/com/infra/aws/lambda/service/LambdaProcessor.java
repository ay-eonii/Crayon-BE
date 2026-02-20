package com.infra.aws.lambda.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mail.application.usecase.MailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LambdaProcessor implements MailSender {

	private final LambdaAsyncClient lambdaAsyncClient;

	@Value("${mail.lambda.arn}")
	private String mailLambdaArn;

	public CompletableFuture<Void> send() {
		InvokeRequest request = InvokeRequest.builder()
			.functionName(mailLambdaArn)
			.invocationType(InvocationType.EVENT)
			.build();

		return lambdaAsyncClient.invoke(request)
			.thenAccept(response -> log.info("[LambdaService] 람다 호출 성공"))
			.exceptionally(e -> {
				log.error("[LambdaService] 람다 호출 중 에러 발생");
				throw new CompletionException(e);
			});
	}
}
