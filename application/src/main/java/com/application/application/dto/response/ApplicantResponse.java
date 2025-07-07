package com.application.application.dto.response;

import com.application.domain.entity.Application;
import lombok.Builder;

@Builder
public record ApplicantResponse(
        String name,
        String email,
        String tel
) {
    public static ApplicantResponse toResponse(Application application) {
        return ApplicantResponse.builder()
                .name(application.getUserName())
                .email(application.getEmail())
                .tel(application.getTel())
                .build();
    }
}
