package com.recruitment.domain.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.club.domain.entity.Club;
import com.form.domain.repository.dto.LinkedRecruitment;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.entity.enums.Step;
import com.recruitment.domain.repository.RecruitmentRepository;
import com.recruitment.exception.RecruitmentNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {

	private final RecruitmentRepository recruitmentRepository;

	public Recruitment find(String recruitmentId) {
		return find(UUID.fromString(recruitmentId));
	}

	public Recruitment find(UUID recruitmentId) {
		return recruitmentRepository.findById(recruitmentId)
			.orElseThrow(RecruitmentNotFoundException::new);
	}

	public Page<Recruitment> findAll(Club club, Pageable pageable) {
		return recruitmentRepository.findAllByClubOrderByCreatedAtDesc(club, pageable);
	}

	public List<String> findAllLinkedRecruitments(String formId) {
		return recruitmentRepository.findAllByFormId(formId)
			.stream()
			.map(Recruitment::getFormId)
			.toList();
	}

	public Map<String, List<LinkedRecruitment>> findAllLinkedRecruitments(List<String> formIds) {
		return recruitmentRepository.findByForms(formIds).stream()
			.collect(Collectors.groupingBy(LinkedRecruitment::formId));
	}

	public List<Step> findAllTypesByRecruitment(Recruitment recruitment) {
		return recruitment.getProcesses().stream()
			.map(Process::getStep)
			.toList();
	}
}
