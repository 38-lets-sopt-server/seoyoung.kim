package org.sopt.domain.user.service;

import org.sopt.domain.user.entiry.User;
import org.sopt.domain.user.dto.UserCreateRequest;
import org.sopt.domain.user.dto.UserResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse signUp(UserCreateRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATE);
        }
        User user = new User(request.nickname(), request.email(), request.password());
        userRepository.save(user);
        return UserResponse.from(user);
    }
}
