package com.item.application.mapper;

import com.item.application.dto.res.DateResponse;
import com.item.domain.entity.Date;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateResponseCreationStrategy implements ItemResponseCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.CALENDAR == type;
    }

    @Override
    public DateResponse create(Item item) {
        return DateResponse.toResponse((Date) item);
    }
}
