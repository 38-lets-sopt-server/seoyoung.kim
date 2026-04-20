package org.sopt.dto.response;

import org.sopt.domain.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        String createdAt
) {
    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
    }
}
