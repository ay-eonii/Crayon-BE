package com.recruitment.domain.service;

import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentDeleteService {

    private final RecruitmentRepository recruitmentRepository;

    public void delete(Recruitment recruitment) {
        recruitmentRepository.delete(recruitment);
    }
}
