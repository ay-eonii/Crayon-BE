package com.recruitment.domain.service;

import com.club.domain.entity.Club;
import com.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import com.recruitment.application.mapper.RecruitmentMapper;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentSaveService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;

    public Recruitment save(Save dto, Club club) {
        return recruitmentRepository.save(recruitmentMapper.from(dto, club));
    }

    public Recruitment save(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }
}
