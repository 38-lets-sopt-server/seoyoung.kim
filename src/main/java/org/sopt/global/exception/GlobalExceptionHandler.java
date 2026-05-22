package org.sopt.global.exception;

import org.sopt.global.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleBusinessException (
            BusinessException e
    ) {
        ErrorCode errorCode = e.getErrorCode();
        BaseResponse<Void> errorResponse = BaseResponse.error(
                errorCode.getCode(),
                e.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // @Valid 검증 실패 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 입력입니다.");

        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        BaseResponse<Void> errorResponse = BaseResponse.error(
                errorCode.getCode(),
                errorMessage
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // JSON 파싱 오류 (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_BODY;
        BaseResponse<Void> errorResponse = BaseResponse.error(
                errorCode.getCode(),
                errorCode.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // 타입 불일치 (400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e
    ) {
        ErrorCode errorCode = ErrorCode.INVALID_TYPE_VALUE;
        BaseResponse<Void> errorResponse = BaseResponse.error(
                errorCode.getCode(),
                errorCode.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // 그 외 예외 처리 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(
            Exception e
    ) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        BaseResponse<Void> errorResponse = BaseResponse.error(
                errorCode.getCode(),
                errorCode.getMessage()
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

}
