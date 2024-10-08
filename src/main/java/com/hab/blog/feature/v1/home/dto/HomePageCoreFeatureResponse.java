package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

@Data
public class HomePageCoreFeatureResponse {
    private String name;
    private String description;
    private String action;

    public HomePageCoreFeatureResponse(String name, String description, String action) {
        this.name = name;
        this.description = description;
        this.action = action;
    }
}
