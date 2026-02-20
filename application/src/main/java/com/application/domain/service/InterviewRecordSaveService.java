package com.application.domain.service;

import com.application.domain.entity.InterviewRecord;
import com.application.domain.repository.InterviewRecordRepository;
import com.application.exception.InterviewRecordAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewRecordSaveService {

    private final InterviewRecordRepository interviewRecordRepository;

    public InterviewRecord save(InterviewRecord interviewRecord) {
        if (interviewRecordRepository.existsByManagerAndApplicationId(interviewRecord.getManager(), interviewRecord.getApplicationId())) {
            throw new InterviewRecordAlreadyExistException();
        }

        return interviewRecordRepository.save(interviewRecord);
    }
}
