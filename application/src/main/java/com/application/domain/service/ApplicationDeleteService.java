package com.application.domain.service;

import com.application.domain.repository.ApplicationRepository;
import com.recruitment.domain.entity.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationDeleteService {
    private final ApplicationRepository applicationRepository;

    public void deleteByRecruitmentId(Recruitment recruitment) {
        applicationRepository.deleteAllByRecruitmentId(recruitment.getId());
    }
}
