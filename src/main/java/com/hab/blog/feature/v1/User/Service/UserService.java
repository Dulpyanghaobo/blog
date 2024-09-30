package com.hab.blog.feature.v1.User.Service;

import com.hab.blog.feature.v1.entities.User.UserPrivacySettings;
import com.hab.blog.feature.v1.User.Dto.*;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfileDto getUserProfile(String username) {
        // 从数据库中获取用户信息
        User user = userRepository.findUsersByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 构建用户资料 DTO
        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setNickname(user.getNickname());
        profileDto.setRelationshipStatus(String.valueOf(user.getRelationshipStatus()));
        profileDto.setGender(String.valueOf(user.getGender()));
        profileDto.setBirthdate(user.getBirthdate());
        profileDto.setBirthTime(user.getBirthTime());
        profileDto.setBirthPlace(user.getBirthPlace());
        profileDto.setCurrentLocation(user.getCurrentLocation());
        profileDto.setTimezone(user.getTimezone());
        profileDto.setDaylightSaving(user.getDaylightSaving());

        // 可以在这里计算星座信息
        profileDto.setZodiacSign(calculateZodiacSign(user.getBirthdate()));
        profileDto.setRisingSign(user.getRisingSign());
        profileDto.setMoonSign(user.getMoonSign());

        // 设置其他属性
        profileDto.setTarotPreference(user.getTarotPreference());
        profileDto.setInterests(user.getInterests());

        UserPrivacySettings privacySettingsEntity = user.getPrivacySettings();
        if (privacySettingsEntity != null) {
            PrivacySettingsDto privacySettings = new PrivacySettingsDto();
            privacySettings.setShareLocation(privacySettingsEntity.isShareLocation());
            privacySettings.setShareBirthdate(privacySettingsEntity.isShareBirthdate());
            privacySettings.setShareGender(privacySettingsEntity.isShareGender());
            profileDto.setPrivacySettings(privacySettings);
        } else {
            profileDto.setPrivacySettings(null); // 设置默认隐私设置
        }

        return profileDto;
    }

    private String calculateZodiacSign(LocalDate birthdate) {
        // 根据出生日期计算星座逻辑
        return "Aries";  // 假设逻辑返回白羊座
    }
}
