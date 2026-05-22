package org.sopt.domain.post.dto;


public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        String createdAt,
        Long likeCount
) {
}
