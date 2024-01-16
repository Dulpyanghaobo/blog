package com.hab.blog.response.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends ApplicationException {

    // Getter方法
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public AlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        // 使用super调用基类的构造函数
        super("ALREADY_EXISTS", String.format("%s with %s : '%s' already exists", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // 如果需要，你还可以添加更多的方法或构造函数来支持其他用例
}
