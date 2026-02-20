package com.application.domain.service;

import com.application.domain.entity.Application;
import com.application.domain.repository.ApplicationJdbcRepository;
import com.application.domain.repository.ApplicationRepository;
import com.application.domain.vo.ApplicationReply;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.RecruitmentRepository;
import com.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationJdbcRepository applicationJdbcRepository;
    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;

    public Application save(UUID recruitmentId, Application application) {
        recruitmentRepository.increaseApplicantCount(recruitmentId);
        return applicationRepository.save(application);
    }

    public List<Application> saveAll(UUID recruitmentId, List<ApplicationReply> applicationReplies) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);
        Process process = recruitment.getDocumentProcess();

        List<Application> applications = applicationReplies.stream()
                .map(ar -> ar.toApplication(recruitmentId, process))
                .toList();

        recruitmentRepository.increaseApplicantCount(recruitmentId, applicationReplies.size());
        return applicationJdbcRepository.batchInsert(applications);
    }
}
