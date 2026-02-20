package com.application.application.dto.response;

import com.application.domain.entity.enums.Rating;

import java.util.Map;

public record RatingResponse(
        long low,
        long medium,
        long high
) {
    public static RatingResponse toResponse(Map<Rating, Long> ratingCount) {
        return new RatingResponse(
                ratingCount.getOrDefault(Rating.LOW, 0L),
                ratingCount.getOrDefault(Rating.MEDIUM, 0L),
                ratingCount.getOrDefault(Rating.HIGH, 0L)
        );
    }
}
