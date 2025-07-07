package com.recruitment.application.validator;

import com.recruitment.application.annotation.PeriodCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.recruitment.application.dto.request.ProcessRequestDTO.Period;

public class PeriodCheckValidator implements ConstraintValidator<PeriodCheck, Period> {

    @Override
    public void initialize(PeriodCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Period period, ConstraintValidatorContext constraintValidatorContext) {
        // 심사 마감 일자가 발표 시작 일자보다 이른지 검사
        return period.evaluation().time().endAt()
                .isBefore(period.announcement().time().startAt().plusMinutes(1));
    }
}
