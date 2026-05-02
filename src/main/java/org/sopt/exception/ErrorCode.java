package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 게시글 관련 에러 (POST_xxx)
    POST_NOT_FOUND("POST_001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_TITLE_REQUIRED("POST_002", "제목은 필수입니다.", HttpStatus.BAD_REQUEST),
    POST_TITLE_TOO_LONG("POST_003", "제목은 50자 이내여야 합니다.", HttpStatus.BAD_REQUEST),

    // 공통 에러 (COMMON_xxx)
    INVALID_INPUT("COMMON_001", "잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("COMMON_002", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST_BODY("COMMON_003", "요청 본문을 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_VALUE("COMMON_004", "잘못된 타입의 값입니다.", HttpStatus.BAD_REQUEST),

    // 유저 관련 에러 (USER_xxx)
    USER_NOT_FOUND("USER_001", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 좋아요 관련 에러 (LIKE_xxx)
    LIKE_ALREADY_EXISTS("LIKE_001", "이미 좋아요를 눌렀습니다.", HttpStatus.CONFLICT),
    LIKE_NOT_FOUND("LIKE_002", "좋아요를 누르지 않은 게시글입니다.", HttpStatus.NOT_FOUND);

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
