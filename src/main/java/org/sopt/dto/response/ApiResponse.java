package org.sopt.dto.response;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.data = data;
        response.message = message;
        return response;
    }

    public static <T> ApiResponse<T> error(String message){
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.data = null;
        response.message = message;
        return response;
    }
}
