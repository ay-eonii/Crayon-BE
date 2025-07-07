package com.application.application.dto.request;

import com.application.domain.entity.enums.Status;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusRequest(

        @NotNull
        Status status
) {
}
