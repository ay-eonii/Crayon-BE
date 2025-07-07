package com.template.domain.entity;

import com.club.domain.entity.Club;
import com.template.application.dto.request.MailTemplateUpdateRequest;
import com.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MailTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mail_template_id")
    private UUID id;

    private String customTemplateName;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public void update(MailTemplateUpdateRequest dto) {
        this.customTemplateName = dto.customTemplateName();
    }
}
