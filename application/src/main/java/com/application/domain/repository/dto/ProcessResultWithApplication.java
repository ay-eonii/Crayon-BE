package com.application.domain.repository.dto;

import com.application.domain.entity.enums.Status;

import java.util.UUID;

public record ProcessResultWithApplication(
        Status status,
        UUID applicationId
) {

    public ProcessResultWithApplication {
        if (status == null) {
            status = Status.BEFORE_EVALUATION;
        }
    }
}
