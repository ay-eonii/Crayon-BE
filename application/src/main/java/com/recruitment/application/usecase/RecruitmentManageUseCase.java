package com.recruitment.application.usecase;

import static com.form.application.dto.response.FormResponseDTO.*;
import static com.recruitment.application.dto.request.RecruitmentRequestDTO.*;
import static com.recruitment.application.dto.response.RecruitmentResponseDTO.Response;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.domain.repository.EvaluationMemoRepository;
import com.application.domain.service.ApplicationDeleteService;
import com.application.domain.service.EvaluationDeleteService;
import com.application.domain.service.EvaluationMemoDeleteService;
import com.club.domain.entity.Club;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubManagerAuthService;
import com.form.application.usecase.FormManageUseCase;
import com.form.domain.entity.Form;
import com.form.domain.service.FormGetService;
import com.recruitment.application.dto.request.RecruitmentUpdateRequest;
import com.recruitment.application.dto.response.RecruitmentDetailsResponse;
import com.recruitment.domain.dto.ProcessWithApplicantCount;
import com.recruitment.domain.dto.RecruitmentCreateResponse;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.service.ProcessDeleteService;
import com.recruitment.domain.service.ProcessGetService;
import com.recruitment.domain.service.ProcessSaveService;
import com.recruitment.domain.service.RecruitmentDeleteService;
import com.recruitment.domain.service.RecruitmentGetService;
import com.recruitment.domain.service.RecruitmentSaveService;
import com.recruitment.domain.service.RecruitmentUpdateService;
import com.recruitment.exception.RecruitmentDeletedException;
import com.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCase {

	private final ClubGetService clubGetService;

	private final RecruitmentGetService recruitmentGetService;
	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentUpdateService recruitmentUpdateService;
	private final RecruitmentDeleteService recruitmentDeleteService;

	private final FormGetService formGetService;
	private final FormManageUseCase formManageUseCase;
	private final ClubManagerAuthService clubManagerAuthService;

	private final ProcessManageUseCase processManageUseCase;
	private final ProcessDeleteService processDeleteService;

	private final ApplicationDeleteService applicationDeleteService;
	private final ProcessSaveService processSaveService;
	private final ProcessGetService processGetService;
	private final EvaluationDeleteService evaluationDeleteService;
	private final EvaluationMemoRepository evaluationMemoRepository;
	private final EvaluationMemoDeleteService evaluationMemoDeleteService;

	@Transactional
	public RecruitmentCreateResponse save(Save dto, User manager) {
		Club club = clubGetService.find(dto.clubId());
		clubManagerAuthService.checkAuthorization(club, manager);

		Recruitment recruitment = recruitmentSaveService.save(dto, club);
		List<Process> processes = processManageUseCase.save(dto.processes(), recruitment);
		recruitment.addProcesses(processes);

		return RecruitmentCreateResponse.from(recruitment);
	}

	@Transactional(readOnly = true)
	public RecruitmentDetailsResponse read(UUID recruitmentId) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		List<ProcessWithApplicantCount> processWithApplicantCounts = processGetService.findAllWithApplicantCount(
			recruitment.getId());
		Info form = formManageUseCase.readForm(recruitment.getFormId());

		return RecruitmentDetailsResponse.toRecruitmentDetailsResponse(recruitment, processWithApplicantCounts, form);
	}

	@Transactional(readOnly = true)
	public Page<Response> readAll(Pageable pageable, String clubId) {
		Club club = clubGetService.find(clubId);
		return recruitmentGetService.findAll(club, pageable)
			.map(Response::toResponse);
	}

	@Transactional
	public void update(String recruitmentId, RecruitmentUpdateRequest request, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		recruitment.checkModifiable();
		recruitment.update(request.title(), request.position(), request.startAt(), request.endAt());
	}

	@Transactional
	public void close(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		recruitmentUpdateService.delete(recruitment);
	}

	@Transactional
	public void activate(String recruitmentId, String formId, User user) {
		checkDeletedRecruitment(recruitmentId);
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);

		Form form = formGetService.find(formId);
		recruitmentUpdateService.update(recruitment, form.getId());
	}

	@Transactional
	public void cancel(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);

		evaluationMemoDeleteService.deleteByRecruitmentId(recruitment.getId());
		evaluationDeleteService.deleteEvaluationByRecruitmentId(recruitment.getId());

		applicationDeleteService.deleteByRecruitmentId(recruitment);

		processDeleteService.deleteAllByRecruitment(recruitment);

		recruitmentDeleteService.delete(recruitment);
	}

	private Recruitment checkAuthorityByRecruitment(String recruitmentId, User manager) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

		return recruitment;
	}

	private void checkDeletedRecruitment(String recruitmentId) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		if (recruitment.getDeletedAt() != null) {
			throw new RecruitmentDeletedException();
		}
	}

	@Transactional
	public void replicate(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		Recruitment newRecruitment = Recruitment.replicate(recruitment);

		List<Process> newProcesses = recruitment.getProcesses()
			.stream()
			.map(Process::replicate)
			.toList();

		newProcesses.forEach(process -> process.addRecruitment(newRecruitment));
		newRecruitment.addNewProcesses(newProcesses);

		processSaveService.saveAll(newProcesses);
		recruitmentSaveService.save(newRecruitment);
	}
}
