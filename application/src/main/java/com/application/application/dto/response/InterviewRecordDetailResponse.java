package com.application.application.dto.response;

import com.application.domain.entity.InterviewRecord;
import com.user.domain.entity.User;
import lombok.Builder;

@Builder
public record InterviewRecordDetailResponse(
        long interviewRecordId,
        String manager,
        boolean isMine,
        String content
) {

    public static InterviewRecordDetailResponse toResponse(InterviewRecord interviewRecord, User manager) {
        return InterviewRecordDetailResponse.builder()
                .interviewRecordId(interviewRecord.getId())
                .manager(interviewRecord.getManager().getName())
                .isMine(interviewRecord.isMine(manager))
                .content(interviewRecord.getContent())
                .build();
    }
}
