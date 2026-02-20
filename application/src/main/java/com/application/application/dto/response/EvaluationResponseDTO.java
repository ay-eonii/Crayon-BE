package com.application.application.dto.response;

import com.application.domain.entity.enums.Rating;
import com.application.domain.entity.enums.Status;

public class EvaluationResponseDTO {

    public record Response(
            Long id,
            Rating rating,
            Status status,
            String managerName,
            String memo
    ) {}
}
