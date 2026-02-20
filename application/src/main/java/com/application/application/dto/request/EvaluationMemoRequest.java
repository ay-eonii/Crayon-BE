package com.application.application.dto.request;

import com.application.domain.entity.Application;
import com.application.domain.entity.EvaluationMemo;
import com.user.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EvaluationMemoRequest(

        @NotBlank
        @Size(max = 255, message = "250자 이하로 작성해주세요")
        String memo
) {
    public EvaluationMemo toEvaluationMemo(User manager, Application application) {
        return EvaluationMemo.builder()
                .memo(memo)
                .processId(application.getProcess().getId())
                .manager(manager)
                .application(application)
                .build();
    }
}
