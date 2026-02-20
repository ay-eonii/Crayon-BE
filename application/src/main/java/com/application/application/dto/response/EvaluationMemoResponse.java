package com.application.application.dto.response;

import com.application.domain.entity.EvaluationMemo;
import com.user.domain.entity.User;
import lombok.Builder;

@Builder
public record EvaluationMemoResponse(
        long id,
        String managerName,
        String memo,
        boolean isMine
) {
    public static EvaluationMemoResponse toResponse(EvaluationMemo memo, User manager) {
        return EvaluationMemoResponse.builder()
                .id(memo.getId())
                .managerName(memo.getManager().getName())
                .memo(memo.getMemo())
                .isMine(memo.getManager() == manager)
                .build();
    }
}
