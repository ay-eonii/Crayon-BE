package com.club.application.mapper;

import com.club.domain.entity.Club;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static com.club.application.dto.request.ClubRequestDTO.Save;
import static com.club.application.dto.response.ClubResponseDTO.Participation;
import static com.club.application.dto.response.ClubResponseDTO.Response;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubMapper {

    Club from(Save dto);

    Response toResponse(Club club);

    Participation toParticipation(Club club);
}
