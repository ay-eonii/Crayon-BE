package com.application.application.usecase;

import com.application.application.dto.request.InterviewRecordRequest;
import com.application.application.dto.response.InterviewRecordDetailResponse;
import com.application.application.dto.response.InterviewRecordResponse;
import com.application.domain.entity.Application;
import com.application.domain.entity.InterviewRecord;
import com.application.domain.service.ApplicationGetService;
import com.application.domain.service.InterviewRecordGetService;
import com.application.domain.service.InterviewRecordSaveService;
import com.application.domain.service.InterviewRecordUpdateService;
import com.club.domain.service.ClubManagerAuthService;
import com.user.domain.entity.User;
import com.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewRecordManageUseCase {

    private final InterviewRecordSaveService interviewRecordSaveService;
    private final UserGetService userGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final ApplicationGetService applicationGetService;
    private final InterviewRecordGetService interviewRecordGetService;
    private final InterviewRecordUpdateService interviewRecordUpdateService;

    @Transactional
    public InterviewRecordResponse saveInterviewRecord(UUID applicationId, User manager, InterviewRecordRequest request) {
        Application application = applicationGetService.find(applicationId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        InterviewRecord interviewRecord = request.toInterviewRecord(manager, application.getId());
        InterviewRecord savedRecord = interviewRecordSaveService.save(interviewRecord);

        return InterviewRecordResponse.toResponse(savedRecord.getId());
    }

    @Transactional(readOnly = true)
    public List<InterviewRecordDetailResponse> readAll(UUID applicationId, User manager) {
        Application application = applicationGetService.find(applicationId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        List<InterviewRecord> interviewRecords = interviewRecordGetService.findAll(applicationId);

        return interviewRecords.stream()
                .map(record -> InterviewRecordDetailResponse.toResponse(record, manager))
                .toList();
    }

    @Transactional
    public void delete(long interviewRecordId, User manager) {
        InterviewRecord interviewRecord = interviewRecordGetService.find(interviewRecordId);
        interviewRecordUpdateService.delete(interviewRecord, manager);
    }

    @Transactional
    public void update(long interviewRecordId, User manager, InterviewRecordRequest request) {
        InterviewRecord interviewRecord = interviewRecordGetService.find(interviewRecordId);
        interviewRecordUpdateService.update(interviewRecord, manager, request.content());
    }
}
