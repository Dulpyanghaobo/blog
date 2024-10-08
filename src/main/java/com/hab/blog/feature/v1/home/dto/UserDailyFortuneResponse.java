package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDailyFortuneResponse {
    private Long id;  // UserDailyFortune ID
    private Integer overallScore;
    private Integer loveScore;
    private Integer wealthScore;
    private Integer careerScore;
    private String encouragementMessage;
    private Boolean isLocked;
    private LocalDate fortuneDate;

    // 静态描述信息
    private String loveModuleDescription;
    private String wealthModuleDescription;
    private String careerModuleDescription;

    // 构造方法
    // 构造方法
    public UserDailyFortuneResponse(Long id, Integer overallScore, Integer loveScore, Integer wealthScore,
                                    Integer careerScore, String encouragementMessage, Boolean isLocked,
                                    LocalDate fortuneDate) {
        this.id = id;
        this.overallScore = overallScore;
        this.loveScore = loveScore;
        this.wealthScore = wealthScore;
        this.careerScore = careerScore;
        this.encouragementMessage = encouragementMessage;
        this.isLocked = isLocked;
        this.fortuneDate = fortuneDate;

        // 静态描述内容
        this.loveModuleDescription = "每日为用户提供一段关于爱情的正面鼓励或思考提示，帮助用户关注感情生活。";
        this.wealthModuleDescription = "每日为用户提供一段关于财富管理、投资建议或财务心态的提示语，帮助用户关注财务健康。";
        this.careerModuleDescription = "每日为用户提供一段关于职业发展或工作态度的鼓励和建议，帮助用户关注职业进步。";
    }
}
