package org.sopt.dto.response;

public record UserLoginResponse(
        Long userId,
        String accessToken,
        String refreshToken
) {
}
