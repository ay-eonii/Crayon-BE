package com.item.domain.service.factory;

import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Item;
import com.item.domain.entity.Score;
import com.item.domain.entity.type.Type;
import org.springframework.stereotype.Service;

@Service
public class ScoreCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SCORE == type;
    }

    @Override
    public Item create(ItemRequest request) {
        return Score.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .required(request.required())
                .meaningOfHigh(request.meaningOfHigh())
                .meaningOfLow(request.meaningOfLow())
                .score(request.score())
                .build();
    }
}
