package com.application.domain.service;

import com.application.domain.entity.Evaluation;
import com.application.domain.repository.EvaluationRepository;
import com.application.exception.EvaluationAlreadyExistException;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationSaveService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation save(User manager, Evaluation evaluation) {
        if (evaluationRepository.existsByManagerAndApplication(manager, evaluation.getApplication())) {
            throw new EvaluationAlreadyExistException();
        }
        return evaluationRepository.save(evaluation);
    }
}
