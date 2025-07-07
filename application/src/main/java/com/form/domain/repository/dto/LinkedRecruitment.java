package com.form.domain.repository.dto;

import java.util.UUID;

public record LinkedRecruitment(
        String formId,
        UUID recruitmentId
) {
}
