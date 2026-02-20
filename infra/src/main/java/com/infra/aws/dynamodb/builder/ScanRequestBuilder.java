package com.infra.aws.dynamodb.builder;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanRequestBuilder {

	public static ScanEnhancedRequest buildScanRequest(Long processId) {
		Expression filterExpression = buildExpression(processId);

		return ScanEnhancedRequest.builder()
			.filterExpression(filterExpression)
			.build();
	}

	private static Expression buildExpression(Long processId) {
		return Expression.builder()
			.expression("processId = :processId")
			.expressionValues(Map.of(
				":processId", AttributeValue.builder().n(processId.toString()).build()
			))
			.build();
	}
}
