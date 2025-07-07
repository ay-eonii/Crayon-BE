package com.recruitment.domain.service;

import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.RecruitmentRepository;
import com.recruitment.exception.ModifiedRecruitmentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentUpdateService {

    private final RecruitmentRepository recruitmentRepository;

    public void update(Recruitment recruitment, String formId) {
        recruitment.activate(formId);
        try {
            recruitmentRepository.save(recruitment);
        } catch (OptimisticLockingFailureException e) {
            throw new ModifiedRecruitmentException();
        }
    }

    public void delete(Recruitment recruitment) {
        recruitment.close();
    }
}
