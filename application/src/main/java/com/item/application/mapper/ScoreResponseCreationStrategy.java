package com.item.application.mapper;

import com.item.application.dto.res.ScoreResponse;
import com.item.domain.entity.Item;
import com.item.domain.entity.Score;
import com.item.domain.entity.type.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreResponseCreationStrategy implements ItemResponseCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SCORE == type;
    }

    @Override
    public ScoreResponse create(Item item) {
        return ScoreResponse.toResponse((Score) item);
    }
}
