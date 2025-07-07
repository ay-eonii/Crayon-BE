package com.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.application.application.dto.response.ApplicationResponseDTO;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.enums.ProcessStep;
import com.recruitment.domain.entity.enums.Step;

import lombok.Builder;

public class ProcessResponseDTO {

	@Builder
	public record Response(
		long id,
		int stage,
		Step step,
		String title,
		LocalDateTime startAt,
		LocalDateTime endAt,
		LocalDateTime announceStartAt,
		LocalDateTime announceEndAt,
		Integer applicantCount,
		ProcessStep processStep,
		LocalDateTime mailScheduledAt
	) {

		public static ProcessResponseDTO.Response toResponse(Process process, long applicantCount) {
			return Response.builder()
				.id(process.getId())
				.stage(process.getStage())
				.step(process.getStep())
				.title(process.getTitle())
				.startAt(process.getStartAt())
				.endAt(process.getEndAt())
				.announceStartAt(process.getAnnounceStartAt())
				.announceEndAt(process.getAnnounceEndAt())
				.applicantCount((int)applicantCount)
				.processStep(process.getProcessStep())
				.mailScheduledAt(process.getMailScheduledAt())
				.build();
		}

	}

	// 기존 Response를 DetailResponse로 변경
	public record DetailResponse(
		Integer stage,
		Step step,
		String title,
		LocalDateTime startAt,
		LocalDateTime endAt,
		LocalDateTime announceStartAt,
		LocalDateTime announceEndAt,
		List<ApplicationResponseDTO.Response> applications,
		Integer applicantCount
	) {
	}

	public record Info(
		Integer stage,
		Step step,
		String title,
		LocalDateTime startAt,
		LocalDateTime endAt,
		LocalDateTime announceStartAt,
		LocalDateTime announceEndAt
	) {
	}

}
