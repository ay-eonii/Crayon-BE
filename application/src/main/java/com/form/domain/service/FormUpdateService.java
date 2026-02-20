package com.form.domain.service;

import com.form.domain.entity.Form;
import com.form.domain.repository.FormRepository;
import com.form.exception.FormCanNotRemoveException;
import com.form.exception.FormUnmodifiableException;
import com.item.application.dto.req.ItemRequest;
import com.item.domain.entity.Item;
import com.item.domain.service.factory.ItemFactory;
import com.recruitment.domain.repository.RecruitmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FormUpdateService {

    private final RecruitmentRepository recruitmentRepository;
    private final ItemFactory itemFactory;
    private final FormRepository formRepository;

    public void update(Form form, String title, String description, List<ItemRequest> rawItems) {
        long linkedRecruitmentCount = recruitmentRepository.countByFormId(form.getId());
        if (linkedRecruitmentCount != 0) {
            throw new FormUnmodifiableException();
        }

        List<Item> items = rawItems.stream()
                .map(itemFactory::createItem)
                .toList();
        form.update(title, description, items);
        formRepository.save(form);
    }

    public void delete(String formId) {
        long linkedRecruitmentCount = recruitmentRepository.countByFormId(formId);
        if (linkedRecruitmentCount != 0) {
            throw new FormCanNotRemoveException();
        }
        formRepository.deleteById(formId);
    }
}
