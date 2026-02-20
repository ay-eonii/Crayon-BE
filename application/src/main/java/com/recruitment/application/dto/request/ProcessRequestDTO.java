package com.recruitment.application.dto.request;

import java.time.LocalDateTime;

import com.recruitment.application.annotation.PeriodCheck;
import com.recruitment.application.annotation.TimeCheck;
import com.recruitment.domain.entity.enums.Step;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProcessRequestDTO {

	public record Save(
		@NotEmpty String title,
		@NotNull Integer stage,
		@NotNull Step step,
		@Valid @PeriodCheck Period period
	) {
	}

	public record Update(
		@NotEmpty String title,
		@NotNull Integer stage,
		@NotNull Step step,
		@Valid @PeriodCheck Period period
	) {
	}

	public record Period(
		@Valid Evaluation evaluation,
		@Valid Announcement announcement
	) {
	}

	public record Evaluation(
		@TimeCheck Time time
	) {
	}

	public record Announcement(
		@TimeCheck Time time
	) {
	}

	public record Time(
		LocalDateTime startAt,
		LocalDateTime endAt
	) {
	}
}
