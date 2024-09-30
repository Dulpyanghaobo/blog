package com.hab.blog.feature.v1.User.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class UserProfileDto {
    private String nickname;
    private String relationshipStatus;
    private String gender;
    private LocalDate birthdate;
    private LocalTime birthTime;
    private String birthPlace;
    private String currentLocation;
    private String timezone;
    private Boolean daylightSaving;
    private String zodiacSign;
    private String risingSign;
    private String moonSign;
    private String tarotPreference;
    private List<String> interests;
    private PrivacySettingsDto privacySettings;
    private List<LinkedAccountDto> linkedAccounts;
    private List<FriendDto> friends;
    private int points;
    private List<RecentActivityDto> recentActivity;
}

