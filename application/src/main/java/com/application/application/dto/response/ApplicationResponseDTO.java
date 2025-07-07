package com.application.application.dto.response;

import com.club.application.dto.response.ClubResponseDTO;
import com.recruitment.application.dto.response.ProcessResponseDTO;

import java.util.List;

public class ApplicationResponseDTO {

    public record Response(
            String id,
            ClubResponseDTO.Response club,
            List<ProcessResponseDTO.Info> processes,
            Integer currentStage
    ) {
    }
}
