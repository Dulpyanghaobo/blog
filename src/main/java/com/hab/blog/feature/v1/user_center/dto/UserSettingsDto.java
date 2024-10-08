package com.hab.blog.feature.v1.user_center.dto;

import lombok.Data;

@Data
public class UserSettingsDto {
    private Boolean backgroundMusic;
    private Boolean dailySignInReminder;
    private Boolean personalizedRecommendations;
    private Boolean friendInteractionReminder;
    private Boolean dailyHoroscopeRecommendation;
    private Boolean privacyAllowProfileView;
    private Boolean allowFriendRequests;
    private Boolean shareActivityStatus;
    private Boolean systemNotifications;
    private Boolean emailNotifications;
    private Boolean darkMode;
    private String fontSize;
    private String language;
}

