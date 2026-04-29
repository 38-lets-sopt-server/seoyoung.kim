package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 50, message = "제목은 최대 50자 이내여야 합니다")
        String title,
        String content,
        Long userId) {
}
