package com.club.application.usecase;

import com.club.application.dto.request.ClubManagerUpdateDto;
import com.club.application.dto.request.ClubRequestDTO;
import com.club.application.dto.response.ClubResponseDTO;
import com.club.application.mapper.ClubMapper;
import com.club.domain.entity.Club;
import com.club.domain.entity.ClubManager;
import com.club.domain.service.ClubGetService;
import com.club.domain.service.ClubManagerDeleteService;
import com.club.domain.service.ClubManagerGetService;
import com.club.domain.service.ClubManagerSaveService;
import com.club.domain.service.ClubUpdateService;
import com.club.domain.service.ClubValidateService;
import com.user.application.dto.response.UserResponseDTO;
import com.user.application.mapper.ManagerMapper;
import com.user.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerUseCase {

    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final ClubValidateService clubValidateService;
    private final ClubMapper clubMapper;
    private final ClubManagerSaveService clubManagerSaveService;
    private final ClubManagerDeleteService clubManagerDeleteService;
    private final ClubManagerGetService clubManagerGetService;
    private final ManagerMapper managerMapper;

    public List<UserResponseDTO.ManagerInfo> getManagers(UUID clubId, User user) {
        clubValidateService.checkAuthority(clubId, user);
        List<ClubManager> clubManagers = clubManagerGetService.readAllManagers(clubId);

        return clubManagers.stream()
                .map(ClubManager::getManager)
                .map(managerMapper::toManagerInfoDTO)
                .toList();
    }

    @Transactional
    public ClubResponseDTO.Participation participate(ClubRequestDTO.Participation dto, User user) {
        Club club = clubGetService.findByCode(dto.code());

        clubManagerSaveService.saveManager(user, club);
        return clubMapper.toParticipation(club);
    }

    public ClubResponseDTO.Code readCode(String clubId, User user) {
        Club club = clubValidateService.checkAuthority(clubId, user);

        return new ClubResponseDTO.Code(club.getCode());
    }

    @Transactional
    public ClubResponseDTO.Code updateCode(String clubId, User user) {
        Club club = clubValidateService.checkAuthority(clubId, user);

        String updatedCode = clubUpdateService.update(club);
        return new ClubResponseDTO.Code(updatedCode);
    }

    @Transactional
    public void deleteManagers(ClubRequestDTO.Delete dto, User user) {
        clubValidateService.checkOwnerAuthority(dto.clubId(), user);

        clubManagerDeleteService.delete(dto.clubId(), dto.userIds(), user);
    }

    @Transactional
    public void updateOwner(ClubManagerUpdateDto dto, User user, UUID clubId) {
        Club club = clubValidateService.checkOwnerAuthority(clubId, user);

        ClubManager owner = clubManagerGetService.findByUserId(club, user);
        ClubManager manager = clubManagerGetService.findByUserId(club, dto.userId());

        manager.toOwner();
        owner.toManager();
    }
}
