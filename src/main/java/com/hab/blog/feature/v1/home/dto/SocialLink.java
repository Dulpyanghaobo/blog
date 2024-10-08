package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

@Data
public class SocialLink {
    private String iconImage;  // 图标的URL或路径
    private String iconTitle;  // 平台名称，如 "WeChat"、"XiaoHongShu"
    private String iconLink;   // 平台的链接

    public SocialLink(String iconImage, String iconTitle, String iconLink) {
        this.iconImage = iconImage;
        this.iconTitle = iconTitle;
        this.iconLink = iconLink;
    }
}
