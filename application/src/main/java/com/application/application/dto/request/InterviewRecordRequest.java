package com.application.application.dto.request;

import com.application.domain.entity.InterviewRecord;
import com.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InterviewRecordRequest(

        @NotNull
        String content
) {
    public InterviewRecord toInterviewRecord(User manager, UUID applicationId) {
        return InterviewRecord.builder()
                .applicationId(applicationId)
                .manager(manager)
                .content(content)
                .build();
    }
}
