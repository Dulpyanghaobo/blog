package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsResponse {
    private String image;  // 当用户未上传个人信息时
    private List<String> imageList;  // 当用户上传个人信息后

    public AdsResponse(String image) {
        this.image = image;
    }

    public AdsResponse(List<String> imageList) {
        this.imageList = imageList;
    }
}

