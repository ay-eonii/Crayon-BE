package com.item.application.mapper;

import com.item.application.dto.res.AnswerResponse;
import com.item.domain.entity.Answer;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResponseCreationStrategy implements ItemResponseCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SHORT_FORM == type || Type.LONG_FORM == type;
    }

    @Override
    public AnswerResponse create(Item item) {
        return AnswerResponse.toResponse((Answer) item);
    }
}
