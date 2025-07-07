package com.item.domain.service.factory;

import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;

public interface ItemCreationStrategy {

    boolean isSupported(Type type);

    Item create(ItemRequest type);
}
