package org.sopt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public boolean isSuccess() { return success; }
    public String getCode() {return code; }
    public String getMessage() { return message; }

    public T getData() { return data; }

    public static <T> BaseResponse<T> success(T data, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.success = true;
        response.message=message;
        response.data = data;
        return response;
    }

    public static <T> BaseResponse<T> error(String code, String message){
        BaseResponse<T> response = new BaseResponse<>();
        response.success = false;
        response.message = message;
        response.code = code;
        return response;
    }
}
