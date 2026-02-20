package com.application.application.dto.request;

import com.application.domain.entity.Application;
import com.application.domain.entity.Evaluation;
import com.application.domain.entity.enums.Rating;
import com.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public record EvaluationRequest(

        @NotNull
        Rating rating
) {
    public Evaluation toEvaluation(User manager, Application application) {
        return Evaluation.builder()
                .rating(rating)
                .manager(manager)
                .application(application)
                .build();
    }
}
