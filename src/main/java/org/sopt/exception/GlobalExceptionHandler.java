package org.sopt.exception;

import org.sopt.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // PostNotFoundException
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostNotFoundException (
            PostNotFoundException e
    ) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<Void> errorResponse = ApiResponse.error(
                errorCode.getCode(),
                e.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // IllegalArgumentException (유효성 검증 실패)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        ApiResponse<Void> errorResponse = ApiResponse.error(
            errorCode.getCode(),
            e.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // 그외 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception e
    ){
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ApiResponse<Void> errorResponse = ApiResponse.error(
                errorCode.getCode(),
                errorCode.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

}
