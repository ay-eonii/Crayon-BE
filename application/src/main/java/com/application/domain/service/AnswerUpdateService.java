package com.application.domain.service;

import com.application.domain.entity.Answer;
import com.application.domain.repository.mongo.AnswerRepository;
import com.application.exception.ApplicationNotFoundException;
import com.item.domain.entity.Item;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerUpdateService {

    private final AnswerRepository answerRepository;

    public void update(UUID applicationId, List<Item> items) {
        Answer answer = answerRepository.findByApplicationId(applicationId.toString())
                .orElseThrow(ApplicationNotFoundException::new);

        answer.update(items);
    }
}
