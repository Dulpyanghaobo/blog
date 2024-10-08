package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

import java.util.List;


@Data
public class AboutUsResponse {
    private String description;
    private String mission;
    private List<SocialLink> socialLinks;  // 包含多个社交平台的列表

    public AboutUsResponse(String description, String mission, List<SocialLink> socialLinks) {
        this.description = description;
        this.mission = mission;
        this.socialLinks = socialLinks;
    }
}
