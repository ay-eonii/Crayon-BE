package com.application.domain.service;

import com.application.domain.entity.Application;
import com.application.domain.repository.ApplicationRepository;
import com.application.exception.AlreadyAppliedException;
import com.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyService {
    private final ApplicationRepository applicationRepository;

    public void checkDuplicate(UUID recruitmentId, User applicant) {
        Optional<Application> application = applicationRepository.findByRecruitmentIdAndUser(recruitmentId, applicant);

        if (application.isPresent()) {
            throw new AlreadyAppliedException();
        }
    }
}
