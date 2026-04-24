package org.sopt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public boolean isSuccess() { return success; }
    public String getCode() {return code; }
    public String getMessage() { return message; }

    public T getData() { return data; }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.message=message;
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> error(String code, String message){
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        response.code = code;
        return response;
    }
}
