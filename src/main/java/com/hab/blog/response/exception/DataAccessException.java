package com.hab.blog.response.exception;

import lombok.Data;

@Data
public class DataAccessException extends ApplicationException{

    private final String detail;
    public DataAccessException(String code, String message, String detail) {
        super(code, message);
        this.detail = detail;
    }
}
