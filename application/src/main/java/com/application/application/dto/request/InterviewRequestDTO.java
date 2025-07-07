package com.application.application.dto.request;

public class InterviewRequestDTO {

    public record Save(
            String place,
            String date
    ) {}
}
