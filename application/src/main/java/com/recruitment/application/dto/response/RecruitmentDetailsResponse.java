package com.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.form.application.dto.response.FormResponseDTO.Info;
import com.recruitment.domain.dto.ProcessWithApplicantCount;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.entity.enums.Step;

import lombok.Builder;

@Builder
public record RecruitmentDetailsResponse(
	String title,
	String generation,
	String position,
	String clubName,
	LocalDateTime startAt,
	Step currentProcess,
	List<ProcessResponse> processes,
	int processCount,
	Info form
) {

	public static RecruitmentDetailsResponse toRecruitmentDetailsResponse(
		Recruitment recruitment,
		List<ProcessWithApplicantCount> processWithApplicantCounts,
		Info form
	) {
		List<ProcessResponse> processes = ProcessResponse.toResponse(processWithApplicantCounts);
		return RecruitmentDetailsResponse.builder()
			.title(recruitment.getTitle())
			.generation(recruitment.getGeneration())
			.position(recruitment.getPosition())
			.clubName(recruitment.getClub().getName())
			.startAt(recruitment.getStartAt())
			.currentProcess(recruitment.getCurrentProcess())
			.processes(processes)
			.processCount(processes.size())
			.form(form)
			.build();
	}
}
