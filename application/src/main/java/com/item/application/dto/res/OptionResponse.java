package com.item.application.dto.res;

import com.item.domain.entity.Option;
import lombok.Builder;

@Builder
public record OptionResponse(
        String id,
        String title,
        boolean selected,
        boolean isEtc
) {
    public static OptionResponse toResponse(Option option) {
        return OptionResponse.builder()
                .id(option.getId())
                .title(option.getTitle())
                .selected(option.isSelected())
                .isEtc(option.isEtc())
                .build();
    }
}
