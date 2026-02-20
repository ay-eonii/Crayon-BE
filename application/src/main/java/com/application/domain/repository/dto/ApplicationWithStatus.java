package com.application.domain.repository.dto;

import com.application.domain.entity.Application;
import com.application.domain.entity.enums.Status;

public record ApplicationWithStatus(
        Application application,
        Status status
) {

    public ApplicationWithStatus {
        if (status == null) {
            status = Status.BEFORE_EVALUATION;
        }
    }
}
