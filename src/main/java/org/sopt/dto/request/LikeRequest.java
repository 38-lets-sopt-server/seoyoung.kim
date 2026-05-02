package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LikeRequest (
   @Schema(description = "유저 ID", example = "1")
   @NotNull(message = "userId는 필수입니다.")
   Long userId
) {
}
