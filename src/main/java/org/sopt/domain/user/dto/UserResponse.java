package org.sopt.domain.user.dto;

import org.sopt.domain.user.entiry.User;

public record UserResponse(
        Long id,
        String nickname,
        String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getNickname(), user.getEmail());
    }
}