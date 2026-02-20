package com.user.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class UserRequestDTO {

    public record Find(
            @NotEmpty String name,
            @NotEmpty String email,
            @NotEmpty String tel
    ) {}
}
