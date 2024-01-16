package com.hab.blog.response.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class MailException extends ApplicationException {

    // 额外的邮件相关信息，例如收件人地址
    private final String recipientAddress;

    public MailException(String code, String message, String recipientAddress) {
        super(code, message);
        this.recipientAddress = recipientAddress;
    }

    // 如果你需要添加额外的逻辑或方法，可以在这里进行扩展
}

