package com.hab.blog.api.v1.auth.Entity;

import lombok.Data;

@Data
public class WeChatLoginResponse {
    private String openid;
    private String session_key;
    private Integer errcode;
    private String errmsg;
}
