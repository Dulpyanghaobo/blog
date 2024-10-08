package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HomePageResponse {
    private LocalDate date;
    private String dayOfWeek;
    private UserAstrologyResponse currentAstrology;
    private UserDailyFortuneResponse dailyFortuneBlock;
    // 新增的模块
    private List<HomePageCoreFeatureResponse> coreFeatures;
    private AdsResponse ads;
    private AboutUsResponse aboutUs;
    public HomePageResponse(LocalDate now, String dayOfWeek, UserAstrologyResponse currentAstrology,
                            UserDailyFortuneResponse dailyFortuneBlock, List<HomePageCoreFeatureResponse> coreFeatures,
                            AdsResponse ads, AboutUsResponse aboutUs) {
        this.date = now;
        this.dayOfWeek = dayOfWeek;
        this.currentAstrology = currentAstrology;
        this.dailyFortuneBlock = dailyFortuneBlock;
        this.coreFeatures = coreFeatures;
        this.ads = ads;
        this.aboutUs = aboutUs;
    }
}

