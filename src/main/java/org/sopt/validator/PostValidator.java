package org.sopt.validator;

public class PostValidator {

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("제목은 50자 이내여야 합니다.");
        }
    }
}
