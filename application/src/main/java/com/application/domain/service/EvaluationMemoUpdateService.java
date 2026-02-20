package com.application.domain.service;


import com.application.domain.repository.EvaluationMemoRepository;
import com.application.exception.MemoDeleteException;
import com.application.exception.MemoUpdateException;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationMemoUpdateService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public void delete(long memoId, User manager) {
        long executeCount = evaluationMemoRepository.deleteByIdAndManager(memoId, manager);
        if (executeCount != 1) {
            throw new MemoDeleteException();
        }
    }

    public void update(String memo, long memoId, User manager) {
        long executeCount = evaluationMemoRepository.updateByIdAndManager(memo, memoId, manager);
        if (executeCount != 1) {
            throw new MemoUpdateException();
        }
    }
}
