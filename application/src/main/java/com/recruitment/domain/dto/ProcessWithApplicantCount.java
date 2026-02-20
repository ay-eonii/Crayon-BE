package com.recruitment.domain.dto;

import com.recruitment.domain.entity.Process;

public record ProcessWithApplicantCount(
        Process process,
        long applicantCount
) {
}
