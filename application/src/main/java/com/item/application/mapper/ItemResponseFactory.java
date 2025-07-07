package com.item.application.mapper;

import com.item.application.dto.res.ItemResponse;
import com.item.domain.entity.Item;
import com.item.domain.entity.type.Type;
import com.item.exception.InvalidItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ItemResponseFactory {

    private final Set<ItemResponseCreationStrategy> creationStrategies;

    public ItemResponse createItem(Item item) {
        Type type = item.getType();
        ItemResponseCreationStrategy itemResponseCreationStrategy = creationStrategies.stream()
                .filter(strategy -> strategy.isSupported(type))
                .findFirst()
                .orElseThrow(InvalidItemException::new);
        return itemResponseCreationStrategy.create(item);
    }
}
