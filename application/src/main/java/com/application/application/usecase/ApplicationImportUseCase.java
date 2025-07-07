package com.application.application.usecase;

import com.application.application.dto.request.ApplicationImportRequest;
import com.application.domain.entity.Application;
import com.application.domain.service.AnswerSaveService;
import com.application.domain.service.ApplicationSaveService;
import com.application.domain.vo.ApplicationReply;
import com.club.domain.service.ClubManagerAuthService;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationImportUseCase {

    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ClubManagerAuthService clubManagerAuthService;

    public void importApplications(UUID recruitmentId, User user, ApplicationImportRequest request) {
        clubManagerAuthService.checkAuthorization(recruitmentId, user);

        List<ApplicationReply> applicationReplies = request.toApplicationReplies();

        List<Application> applications = applicationSaveService.saveAll(recruitmentId, applicationReplies);
        answerSaveService.save(applicationReplies, applications);
    }
}
