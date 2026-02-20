package com.item.domain.service.factory;

import com.application.domain.vo.ApplicationReply;
import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Answer;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SHORT_FORM == type || Type.LONG_FORM == type;
    }

    @Override
    public Item create(ItemRequest request) {
        return Answer.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .required(request.required())
                .answer(request.answer())
                .maxLength(request.maxLength())
                .build();
    }

    public List<Item> create(ApplicationReply applicationReply) {
        return applicationReply.toAnswers();
    }
}
