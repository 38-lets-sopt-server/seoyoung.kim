package org.sopt.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sopt.domain.user.dto.UserCreateRequest;
import org.sopt.domain.user.dto.UserResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(UserCreateRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATE);
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.nickname(), request.email(), encodedPassword);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATE);
        }
        return UserResponse.from(user);
    }
}
