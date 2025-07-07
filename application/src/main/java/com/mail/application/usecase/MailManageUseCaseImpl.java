package com.mail.application.usecase;

import static com.mail.presentation.constant.ResponseMessage.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.domain.repository.dto.ApplicationWithStatus;
import com.application.domain.service.ApplicationGetService;
import com.club.domain.entity.Club;
import com.club.domain.service.ClubManagerAuthService;
import com.global.common.util.BatchDivider;
import com.mail.application.dto.request.MailRequest;
import com.mail.application.dto.request.MailUpdateRequest;
import com.mail.domain.entity.CommonData;
import com.mail.domain.entity.Mail;
import com.mail.domain.entity.MailInfo;
import com.mail.domain.entity.Mails;
import com.mail.domain.service.MailLimiter;
import com.mail.domain.service.MailProcessorFacade;
import com.mail.domain.service.MailUpdateService;
import com.mail.exception.DynamodbUploadException;
import com.mail.exception.LambdaInvokeException;
import com.mail.exception.MailCancelException;
import com.mail.exception.MailLimitExceededException;
import com.mail.exception.MailUpdateException;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.service.ProcessGetService;
import com.template.domain.service.MailTemplateService;
import com.user.domain.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailManageUseCaseImpl {

	private static final int PAGE_SIZE = 100;

	private final MailProcessorFacade mailProcessorFacade;
	private final MailUpdateService mailUpdateService;
	private final ApplicationGetService applicationGetService;
	private final ProcessGetService processGetService;
	private final MailTemplateService mailTemplateService;
	private final MailSender mailSender;
	private final ClubManagerAuthService clubManagerAuthService;
	private final MailLimiter mailLimiterWithLuaScript;

	@Transactional
	public void reserve(MailRequest dto, User user) {
		Process process = checkAuthorityByProcessId(dto.processId(), user);
		process.reserve(dto.scheduledTime());

		Mails mails = createMails(dto, process);
		uploadMail(mails);
	}

	@Transactional
	public void direct(MailRequest dto, User user) {
		Process process = checkAuthorityByProcessId(dto.processId(), user);
		process.reserve(dto.scheduledTime());

		Mails mails = createMails(dto, process);
		checkLimit(process.getRecruitment().getClub(), mails.count());

		uploadMail(mails);
		sendMails();
	}

	private Mails createMails(MailRequest dto, Process process) {
		List<ApplicationWithStatus> applicationWithStatuses = applicationGetService.findAllWithProcessResult(process);
		UUID passTemplateId = mailTemplateService.uploadTemplate(dto.passTemplate());
		UUID failTemplateId = mailTemplateService.uploadTemplate(dto.failTemplate());
		CommonData commonData = CommonData.of(process, process.getRecruitment());
		MailInfo mailInfo = dto.toMailInfo(passTemplateId, failTemplateId, commonData);

		return Mails.create(applicationWithStatuses, mailInfo);
	}

	private void sendMails() {
		CompletableFuture<Void> result = mailSender.send();

		result.thenRun(() ->
			log.info("[MailManageUseCaseImpl] 메일 전송 성공")
		).exceptionally(e -> {
			throw new LambdaInvokeException(e.getMessage());
		});
	}

	private void checkLimit(Club club, int amount) {
		boolean consumed = mailLimiterWithLuaScript.tryConsume(club.getId(), amount);
		if (!consumed) {
			throw new MailLimitExceededException(MAIL_LIMIT_EXCEEDED.getMessage());
		}
	}

	@Transactional
	public void cancel(long processId, User user) {
		Process process = checkAuthorityByProcessId(processId, user);

		process.checkMailScheduled();

		try {
			mailUpdateService.cancelMail(processId).join();
			process.cancelMail();
		} catch (CompletionException e) {
			throw new MailCancelException(e.getMessage());
		}
	}

	@Transactional
	public void update(long processId, MailUpdateRequest dto, User user) {
		Process process = checkAuthorityByProcessId(processId, user);

		process.checkMailScheduled();

		if (dto.scheduledTime().isBefore(LocalDateTime.now())) {
			throw new MailUpdateException(MAIL_UPDATE_FAIL.getMessage());
		}

		try {
			mailUpdateService.updateScheduledTime(processId, dto).join();
			process.updateSchedule(dto.scheduledTime());
		} catch (CompletionException e) {
			throw new MailUpdateException(e.getMessage());
		}
	}

	private void uploadMail(Mails mails) {
		List<Mail> mails1 = mails.getMails();
		List<CompletableFuture<Void>> uploadFutures = BatchDivider.divide(mails1, PAGE_SIZE)
			.stream()
			.map(mailProcessorFacade::process)
			.toList();

		CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0]))
			.thenRun(() -> log.info("[MailSaveService] | {} 개의 메일 비동기 업로드 성공", mails1.size()))
			.exceptionally(ex -> {
				log.error("[MailSaveService] | 비동기 업로드 중 예외 발생: {}", ex.getMessage(), ex);
				throw new CompletionException(ex);
			});

		checkUpload(uploadFutures);
	}

	private void checkUpload(List<CompletableFuture<Void>> uploadFutures) {
		try {
			CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0])).join();
			log.info("[MailManageUseCaseImpl] | 메일 예약 및 DynamoDB 업로드 성공!");
		} catch (CompletionException ex) {
			log.error("[MailManageUseCaseImpl] | DynamoDB 업로드 중 예외 발생: {}", ex.getMessage());
			throw new DynamodbUploadException();
		}
	}

	private Process checkAuthorityByProcessId(Long processId, User user) {
		Process process = processGetService.find(processId);
		clubManagerAuthService.checkAuthorization(process, user);

		return process;
	}
}
