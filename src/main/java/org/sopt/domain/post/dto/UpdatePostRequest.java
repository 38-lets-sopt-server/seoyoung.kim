package org.sopt.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @Schema(description = "수정할 제목", example = "사실 오늘 학식")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 50, message = "제목은 최대 50자 이내여야 합니다")
        String title,

        @Schema(description = "수정할 내용", example = "샤브샤브래!")
        String content) {
}


