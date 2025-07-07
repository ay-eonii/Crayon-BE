package com.user.application.mapper;

import com.user.application.dto.response.UserResponseDTO.ManagerInfo;
import com.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {
    ManagerInfo toManagerInfoDTO(User user);
}
