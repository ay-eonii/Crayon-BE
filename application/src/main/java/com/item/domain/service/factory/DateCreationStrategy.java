package com.item.domain.service.factory;

import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Date;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.CALENDAR == type;
    }

    @Override
    public Item create(ItemRequest request) {
        return Date.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .answer(request.answer())
                .required(request.required())
                .build();
    }
}
