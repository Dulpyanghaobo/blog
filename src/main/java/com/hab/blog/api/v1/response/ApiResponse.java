package com.hab.blog.api.v1.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 静态工厂方法用于成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Operation successful", data);
    }

    // 静态工厂方法用于失败响应
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(500, message, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
