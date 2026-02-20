package com.application.domain.service;

import com.application.domain.entity.Application;
import com.application.domain.entity.EvaluationMemo;
import com.application.domain.repository.EvaluationMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationMemoGetService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public List<EvaluationMemo> findAll(Application application) {
        return evaluationMemoRepository.findAllByApplication(application);
    }
}
