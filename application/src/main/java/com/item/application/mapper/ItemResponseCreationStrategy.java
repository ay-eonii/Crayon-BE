package com.item.application.mapper;

import com.item.application.dto.res.ItemResponse;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;

public interface ItemResponseCreationStrategy {

    boolean isSupported(Type type);

    ItemResponse create(Item item);
}
