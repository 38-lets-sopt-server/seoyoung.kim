package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.dto.UserLoginRequest;
import org.sopt.global.dto.BaseResponse;
import org.sopt.domain.auth.dto.TokenResponse;
import org.sopt.domain.user.dto.UserResponse;
import org.sopt.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @Valid @RequestBody UserLoginRequest request
    ) {
        TokenResponse tokens = authService.login(request.email(), request.password());
        return ResponseEntity.ok(BaseResponse.success(tokens, "로그인 성공"));
    }

    @Operation(summary = "로그아웃")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청")
    })
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            @RequestHeader("Authorization") String authorizationHeader,
            Authentication authentication
    ) {
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        Long userId = Long.parseLong(authentication.getName());
        authService.logout(userId, token);
        return ResponseEntity.ok(BaseResponse.success("로그아웃 성공"));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }
        Long memberId = Long.parseLong(authentication.getName());
        UserResponse user = authService.getUserById(memberId);
        return ResponseEntity.ok(BaseResponse.success(user, "내 정보 조회 성공"));
    }
}
