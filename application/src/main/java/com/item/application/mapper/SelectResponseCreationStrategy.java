package com.item.application.mapper;

import com.item.application.dto.res.SelectResponse;
import com.item.domain.entity.Item;
import com.item.domain.entity.Select;
import com.item.domain.entity.type.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelectResponseCreationStrategy implements ItemResponseCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SELECT == type || Type.MULTI_SELECT == type;
    }

    @Override
    public SelectResponse create(Item item) {
        return SelectResponse.toResponse((Select) item);
    }
}
