package com.application.domain.repository.dto;

import com.recruitment.domain.entity.Process;

public record ProcessApplicant(
        Process process,
        long applicantCount
) {
}
