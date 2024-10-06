package com.hab.blog.feature.v1.user_center.dto;

import lombok.Data;

@Data
public class PrivacySettingsDto {
    private Boolean shareGender;
    private Boolean shareBirthdate;
    private Boolean shareLocation;
}
