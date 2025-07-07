package com.landing.application.mapper;

import com.club.domain.entity.Club;
import com.landing.application.dto.response.LandingResponseDTO.General;
import com.landing.application.dto.response.LandingResponseDTO.Style;
import com.landing.domain.entity.Landing;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LandingMapper {
    General toGeneralResponse(Club club, Landing landing);

    Style toStyleResponse(Club club, Landing landing);
}
