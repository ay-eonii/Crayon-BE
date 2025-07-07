package com.user.domain.service;

import com.user.domain.entity.User;
import com.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
    private final UserRepository userRepository;

    public User save(User manager) {
        return userRepository.save(manager);
    }
}
