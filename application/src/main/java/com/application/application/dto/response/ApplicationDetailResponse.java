package com.application.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.application.domain.entity.Answer;
import com.application.domain.entity.Application;
import com.application.domain.entity.Interview;
import com.application.domain.entity.ProcessResult;
import com.application.domain.entity.enums.Status;
import com.recruitment.domain.entity.enums.Step;

public record ApplicationDetailResponse(
	String id,
	ApplicantResponse user,
	Status status,
	Interview interview,
	boolean isBeforeInterview,
	int currentStage,
	String currentStageTitle,
	LocalDateTime createdAt,
	AnswerResponseDTO.Response answer
) {

	public static ApplicationDetailResponse toResponse(Application application, Answer answer, List<Step> steps,
		ProcessResult processResult) {
		return new ApplicationDetailResponse(
			application.getId().toString(),
			ApplicantResponse.toResponse(application),
			processResult.getStatus(),
			application.getInterview(),
			application.isBeforeInterview(steps),
			application.getProcess().getStage(),
			application.getProcess().getTitle(),
			application.getCreatedAt(),
			answer == null ? null : AnswerResponseDTO.Response.toAnswerResponse(answer)
		);
	}
}
