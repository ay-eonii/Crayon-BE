package com.application.domain.service;

import com.application.application.mapper.AnswerMapper;
import com.application.domain.entity.Answer;
import com.application.domain.entity.Application;
import com.application.domain.repository.mongo.AnswerRepository;
import com.application.domain.vo.ApplicationReply;
import com.application.exception.ApplicationReplySizeMismatchException;
import com.item.domain.entity.Item;
import com.item.domain.service.factory.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AnswerSaveService {

    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;
    private final ItemFactory itemFactory;

    public Answer save(List<Item> items, UUID applicationId) {
        Answer answer = answerMapper.from(items, applicationId);
        return answerRepository.save(answer);
    }

    public void save(List<ApplicationReply> applicationReplies, List<Application> applications) {
        if (applicationReplies.size() != applications.size()) {
            throw new ApplicationReplySizeMismatchException();
        }

        List<Answer> answers = IntStream.range(0, applications.size())
                .mapToObj(i -> createAnswer(applicationReplies, applications, i))
                .toList();
        answerRepository.saveAll(answers);
    }

    private Answer createAnswer(List<ApplicationReply> applicationReplies, List<Application> applications, int i) {
        UUID applicationId = applications.get(i).getId();
        List<Item> items = itemFactory.createItem(applicationReplies.get(i));
        return Answer.builder()
                .applicationId(applicationId.toString())
                .items(items)
                .build();
    }
}
