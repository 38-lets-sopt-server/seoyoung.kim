package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @Schema(description = "게시글 제목", example = "오늘 학식 뭐임?")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 50, message = "제목은 최대 50자 이내여야 합니다")
        String title,

        @Schema(description = "게시글 내용", example = "돈까스래")
        String content,

        @Schema(description = "작성자 ID", example = "1")
        @NotNull(message = "userId는 필수입니다.")
        Long userId) {
        }
