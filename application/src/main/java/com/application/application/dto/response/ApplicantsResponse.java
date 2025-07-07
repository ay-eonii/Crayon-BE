package com.application.application.dto.response;

import com.application.domain.entity.Application;

import java.util.List;
import java.util.UUID;

public record ApplicantsResponse(
        UUID id,
        String name
) {
    public static List<ApplicantsResponse> toResponse(List<Application> applications) {
        return applications.stream()
                .map(ApplicantsResponse::toResponse)
                .toList();
    }

    private static ApplicantsResponse toResponse(Application application) {
        return new ApplicantsResponse(
                application.getId(),
                application.getUserName()
        );
    }
}
