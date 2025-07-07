package com.application.application.usecase;

import com.application.application.dto.request.ApplicationSaveRequest;
import com.application.application.dto.request.ApplicationUpdateRequest;
import com.application.application.dto.response.ApplicationResponseDTO.Response;
import com.application.application.dto.response.MyApplicationResponse;
import com.application.application.mapper.ApplicationMapper;
import com.application.domain.entity.Answer;
import com.application.domain.entity.Application;
import com.application.domain.service.AnswerGetService;
import com.application.domain.service.AnswerSaveService;
import com.application.domain.service.AnswerUpdateService;
import com.application.domain.service.ApplicationGetService;
import com.application.domain.service.ApplicationSaveService;
import com.application.domain.service.ApplicationUpdateService;
import com.application.domain.service.ApplicationVerifyService;
import com.item.application.usecase.ItemManageUseCase;
import com.item.domain.entity.Item;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.service.RecruitmentGetService;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationMapper applicationMapper;
    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ApplicationGetService applicationGetService;
    private final AnswerGetService answerGetService;
    private final AnswerUpdateService answerUpdateService;
    private final ApplicationUpdateService applicationUpdateService;
    private final ItemManageUseCase itemManageUseCase; // todo 삭제
    private final ApplicationVerifyService applicationVerifyService;

    @Transactional
    public void apply(ApplicationSaveRequest dto, UUID recruitmentId, User applicant) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        recruitment.checkAvailable();

        applicationVerifyService.checkDuplicate(recruitment.getId(), applicant);

        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = dto.toApplication(recruitment, applicant);

        applicationSaveService.save(recruitment.getId(), application);
        answerSaveService.save(items, application.getId());
    }

    @Transactional(readOnly = true)
    public List<Response> readAll(User applicant) {
        return applicationGetService.findAll(applicant).stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MyApplicationResponse read(String applicationId, User applicant) {
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);

        Answer answer = answerGetService.findByApplicationId(application.getId());

        return MyApplicationResponse.toResponse(application, answer);
    }

    @Transactional
    public void update(String applicationId, ApplicationUpdateRequest dto, User applicant) {
        Application application = applicationGetService.find(applicationId);
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        application.checkAuthorization(applicant);
        recruitment.checkAvailable();

        List<Item> items = itemManageUseCase.create(dto.answers());
        answerUpdateService.update(application.getId(), items);
    }

    @Transactional
    public void delete(String applicationId, User applicant) {
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);
        applicationUpdateService.delete(application);
    }
}
