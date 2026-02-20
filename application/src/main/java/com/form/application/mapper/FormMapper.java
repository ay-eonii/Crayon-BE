package com.form.application.mapper;

import static com.form.application.dto.response.FormResponseDTO.DetailResponse;
import static com.form.application.dto.response.FormResponseDTO.Info;
import static com.form.application.dto.response.FormResponseDTO.Response;

import com.form.application.dto.request.FormRequestDTO;
import com.form.domain.entity.Form;
import com.item.domain.entity.Item;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FormMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Form from(FormRequestDTO.Save dto, List<Item> items, String clubId);

    DetailResponse toDetailResponse(Form form, List<String> recruitmentIds);

    Info toInfo(Form form);

    Response toResponse(Form form);
}
