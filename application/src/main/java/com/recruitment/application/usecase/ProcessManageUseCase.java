package com.recruitment.application.usecase;

import static com.recruitment.application.dto.request.ProcessRequestDTO.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.domain.service.ClubManagerAuthService;
import com.recruitment.application.dto.response.ProcessResponse;
import com.recruitment.domain.dto.ProcessWithApplicantCount;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.entity.enums.ProcessStep;
import com.recruitment.domain.entity.enums.Step;
import com.recruitment.domain.service.ProcessDeleteService;
import com.recruitment.domain.service.ProcessGetService;
import com.recruitment.domain.service.ProcessSaveService;
import com.recruitment.domain.service.RecruitmentGetService;
import com.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessManageUseCase {

	private final ProcessSaveService processSaveService;
	private final ProcessDeleteService processDeleteService;
	private final RecruitmentGetService recruitmentGetService;
	private final ClubManagerAuthService clubManagerAuthService;
	private final ProcessGetService processGetService;

	public List<Process> save(List<Save> dto, Recruitment recruitment) {
		return processSaveService.saveAll(dto, recruitment);
	}

	@Transactional(readOnly = true)
	public List<ProcessResponse> readAll(UUID recruitmentId, User user) {
		clubManagerAuthService.checkAuthorization(recruitmentId, user);
		List<ProcessWithApplicantCount> processWithApplicantCount = processGetService.findAllWithApplicantCount(
			recruitmentId);

		return ProcessResponse.toResponse(processWithApplicantCount);
	}

	@Transactional
	public List<Process> update(List<Update> dto, Recruitment recruitment) {
		processDeleteService.deleteAll(recruitment.getProcesses());
		recruitment.clearProcesses();

		return dto.stream()
			.map(update -> processSaveService.save(update, recruitment))
			.sorted(Comparator.comparing(Process::getStage))
			.toList();
	}

	@Transactional
	public void updateStep(UUID recruitmentId, Long processId, ProcessStep step, User user) {
		clubManagerAuthService.checkAuthorization(recruitmentId, user);

		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		Process process = processGetService.find(processId);

		Step currentProcess = recruitment.getCurrentProcess();

		process.checkMovable(currentProcess, step);

		process.updateStep(step);
	}
}

