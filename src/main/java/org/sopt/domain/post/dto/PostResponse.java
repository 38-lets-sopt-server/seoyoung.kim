package org.sopt.domain.post.dto;

import org.sopt.domain.post.entity.Post;
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

    public static PostResponse from(Post post, long likeCount) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getId(),
                post.getCreatedAt().toString(),
                likeCount
        );
    }
}
