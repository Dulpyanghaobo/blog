package com.hab.blog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {
    private String appid;
    private String secret;
    private String apiJscode2session;

    // Getters and Setters
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getApiJscode2session() {
        return apiJscode2session;
    }

    public void setApiJscode2session(String apiJscode2session) {
        this.apiJscode2session = apiJscode2session;
    }
}

