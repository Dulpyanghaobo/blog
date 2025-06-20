package com.hab.blog.api.v1.response.exception;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException{
    private final String code;
    private long timestamp;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }
}
