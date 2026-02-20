package com.infra.aws.s3.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.landing.application.usecase.dto.LandingDeleteEvent;
import com.infra.aws.cloudfront.Service.CloudfrontService;
import com.infra.aws.exception.DeletionFailedException;
import com.infra.aws.route53.service.Route53Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteLandingEventListener {
	private final S3Service s3Service;
	private final CloudfrontService cloudfrontService;
	private final Route53Service route53Service;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void processUpload(LandingDeleteEvent uploadEvent) {
		String subdomain = uploadEvent.subdomain();
		try {
			cloudfrontService.disableDitribute(subdomain);
			s3Service.delete(subdomain);
			route53Service.delete(subdomain);
		} catch (Exception e) {
			throw new DeletionFailedException(e);
		}
	}
}
