package com.hab.blog.feature.v1.auth.Dto;

import lombok.Data;

@Data
public class WeChatLoginResponse {
    private String openid;
    private String session_key;
    private Integer errcode;
    private String errmsg;
    private String unionid;
}
