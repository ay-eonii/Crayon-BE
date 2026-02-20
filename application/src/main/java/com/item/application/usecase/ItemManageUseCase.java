package com.item.application.usecase;

import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Item;

import java.util.List;

public interface ItemManageUseCase {
    List<Item> create(List<ItemRequest> request);
}
