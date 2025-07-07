package com.application.application.usecase;


import com.application.application.dto.request.InterviewRequestDTO;
import com.application.application.mapper.InterviewMapper;
import com.application.domain.entity.Application;
import com.application.domain.entity.Interview;
import com.application.domain.service.ApplicationGetService;
import com.application.domain.service.ApplicationUpdateService;
import com.club.domain.service.ClubManagerAuthService;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewManageUseCase {

    private final InterviewMapper interviewMapper;
    private final ApplicationUpdateService applicationUpdateService;
    private final ApplicationGetService applicationGetService;
    private final ClubManagerAuthService clubManagerAuthService;

    public void saveInterview(String applicationId, InterviewRequestDTO.Save dto, User user) {
        Application application = checkAuthorityByApplication(applicationId, user);
        Interview interview = interviewMapper.from(dto);
        applicationUpdateService.update(application, interview);
    }

    private Application checkAuthorityByApplication(String applicationId, User manager) {
        Application application = applicationGetService.find(applicationId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        return application;
    }
}
