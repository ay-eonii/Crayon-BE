package com.application.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApplicationMoveRequest(
        @NotNull int fromStage,
        @NotNull int toStage
) {
}
