package com.application.domain.service;

import com.application.domain.entity.EvaluationMemo;
import com.application.domain.repository.EvaluationMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationMemoSaveService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public EvaluationMemo save(EvaluationMemo memo) {
        return evaluationMemoRepository.save(memo);
    }
}
