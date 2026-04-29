package org.sopt.validator;

import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;

public class PostValidator {

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.POST_TITLE_REQUIRED);
        }
        if (title.length() > 50) {
            throw new BusinessException(ErrorCode.POST_TITLE_TOO_LONG);
        }
    }
}
