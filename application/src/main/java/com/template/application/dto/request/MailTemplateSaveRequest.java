package com.template.application.dto.request;

import com.club.domain.entity.Club;
import com.template.domain.entity.MailTemplate;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MailTemplateSaveRequest(
        @NotBlank String customTemplateName,
        @NotBlank String subject,
        @NotBlank String htmlPart,
        @NotBlank String textPart
) {
    public MailTemplate toMailTemplate(Club club) {
        return MailTemplate.builder()
                .id(UUID.randomUUID())
                .customTemplateName(customTemplateName)
                .club(club)
                .build();
    }
}
