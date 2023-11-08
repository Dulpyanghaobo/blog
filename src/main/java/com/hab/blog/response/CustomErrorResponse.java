package com.hab.blog.response;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String errorCode;
    private String errorMsg;
    private long timestamp;

    public CustomErrorResponse(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and setters
}

