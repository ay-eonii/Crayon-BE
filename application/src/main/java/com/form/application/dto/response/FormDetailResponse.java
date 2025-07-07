package com.form.application.dto.response;

import com.club.domain.entity.Club;
import com.form.domain.entity.Form;
import com.item.application.dto.res.ItemResponse;
import com.recruitment.domain.entity.Recruitment;
import lombok.Builder;

import java.util.List;

@Builder
public record FormDetailResponse(
        String clubName,
        String title,
        String position,
        List<ItemResponse> items
) {

    public static FormDetailResponse toResponse(Club clubName, Recruitment recruitment, Form form, List<ItemResponse> itemResponses) {
        return FormDetailResponse.builder()
                .clubName(clubName.getName())
                .title(form.getTitle())
                .position(recruitment.getPosition())
                .items(itemResponses)
                .build();
    }
}
