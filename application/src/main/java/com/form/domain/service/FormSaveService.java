package com.form.domain.service;

import com.form.application.dto.request.FormRequestDTO.Save;
import com.form.application.mapper.FormMapper;
import com.form.domain.entity.Form;
import com.form.domain.repository.FormRepository;
import com.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormSaveService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;

    public Form save(Form form) {
        return formRepository.save(form);
    }

    public Form save(Save dto, List<Item> items, String clubId) {
        return formRepository.save(formMapper.from(dto, items, clubId));
    }
}

