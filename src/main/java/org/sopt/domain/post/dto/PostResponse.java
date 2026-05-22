package org.sopt.domain.post.dto;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        String createdAt,
        Long likeCount
) {
    // JPQL new 키워드용
    public PostResponse(Long id, String title, String content, Long userId, LocalDateTime createdAt, long likeCount) {
        this(id, title, content, userId, createdAt.toString(), likeCount);
    }
}
