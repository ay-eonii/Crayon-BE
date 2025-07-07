package com.form.application.usecase;

import com.club.domain.entity.Club;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubManagerAuthService;
import com.form.application.dto.request.FormRequestDTO.Update;
import com.form.application.dto.response.FormDetailResponse;
import com.form.application.dto.response.FormResponseDTO.DetailResponse;
import com.form.application.dto.response.FormResponseDTO.Info;
import com.form.application.dto.response.FormResponseDTO.Response;
import com.form.application.dto.response.FormResponseDTO.SaveResponse;
import com.form.domain.entity.Form;
import com.form.domain.repository.dto.LinkedRecruitment;
import com.form.domain.service.FormGetService;
import com.form.domain.service.FormSaveService;
import com.form.domain.service.FormUpdateService;
import com.item.application.dto.res.ItemResponse;
import com.item.application.mapper.ItemResponseFactory;
import com.item.application.usecase.ItemManageUseCase;
import com.item.domain.entity.Item;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.service.RecruitmentGetService;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.form.application.dto.request.FormRequestDTO.Save;
import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class FormManageUseCase {

    private final FormSaveService formSaveService;
    private final ItemManageUseCase itemManageUseCase;
    private final ClubGetService clubGetService;
    private final FormGetService formGetService;
    private final FormUpdateService formUpdateService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final RecruitmentGetService recruitmentGetService;
    private final ItemResponseFactory itemResponseFactory;

    @Transactional(readOnly = true)
    public DetailResponse read(String id) {
        Form form = formGetService.find(id);
        List<String> linkedRecruitmentIds = recruitmentGetService.findAllLinkedRecruitments(form.getId());

        List<ItemResponse> itemResponses = form.getItems()
                .stream()
                .map(itemResponseFactory::createItem)
                .toList();

        return DetailResponse.toResponse(form, itemResponses, linkedRecruitmentIds);// select
    }

    @Transactional(readOnly = true)
    public Info readForm(String id) {
        Optional<Form> form = formGetService.findAsOptional(id);

        return Info.toInfo(form);
    }

    @Transactional(readOnly = true)
    public List<Response> readAll(User user, String clubId) {
        checkAuthorityByClubId(user, clubId);

        List<Form> forms = formGetService.findAll(clubId);
        List<String> formIds = formGetService.findAllIds(forms);
        Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(
                formIds);

        return forms.stream()
                .map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
                .toList();
    }

    @Transactional
    public SaveResponse create(Save dto, String clubId, User user) {
        checkAuthorityByClubId(user, clubId);
        List<Item> items = itemManageUseCase.create(dto.itemRequests());
        Form form = formSaveService.save(dto, items, clubId);

        return new SaveResponse(form.getId());
    }

    @Transactional
    public void update(String formId, Update dto, User user) {
        Form form = checkAuthorityByFormId(user, formId);
        formUpdateService.update(form, dto.title(), dto.description(), dto.itemRequests());
    }

    @Transactional
    public void delete(String formId, User user) {
        checkAuthorityByFormId(user, formId);
        formUpdateService.delete(formId);
    }

    @Transactional(readOnly = true)
    public List<Response> search(String keyword, String clubId, User user) {
        checkAuthorityByClubId(user, clubId);

        List<Form> forms = formGetService.searchByKeyword(keyword, clubId);
        List<String> formIds = formGetService.findAllIds(forms);
        Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(
                formIds);

        return forms.stream()
                .map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
                .toList();
    }

    @Transactional(readOnly = true)
    public FormDetailResponse read(UUID recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Club club = recruitment.getClub();
        Form form = formGetService.find(recruitment.getFormId());

        List<ItemResponse> itemResponses = form.getItems()
                .stream()
                .map(itemResponseFactory::createItem)
                .toList();

        return FormDetailResponse.toResponse(club, recruitment, form, itemResponses);
    }

    private Form checkAuthorityByFormId(User user, String formId) {
        Form form = formGetService.find(formId);
        checkAuthorityByClubId(user, form.getClubId());

        return form;
    }

    private void checkAuthorityByClubId(User manager, String clubId) {
        Club club = clubGetService.find(clubId);
        clubManagerAuthService.checkAuthorization(club, manager);
    }

    public void replicate(String formId, User user) {
        Form form = checkAuthorityByFormId(user, formId);
        Form newForm = Form.replicate(form);
        formSaveService.save(newForm);
    }
}
