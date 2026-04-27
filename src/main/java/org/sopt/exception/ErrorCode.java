package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 게시글 관련 에러 (POST_xxx)
    POST_NOT_FOUND("POST_001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_TITLE_REQUIRED("POST_002", "제목은 필수입니다.", HttpStatus.BAD_REQUEST),
    POST_TITLE_TOO_LONG("POST_003", "제목은 50자 이내여야 합니다.", HttpStatus.BAD_REQUEST),

    // 공통 에러 (COMMON_xxx)
    INVALID_INPUT("COMMON_001", "잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("COMMON_002", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus= httpStatus;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
