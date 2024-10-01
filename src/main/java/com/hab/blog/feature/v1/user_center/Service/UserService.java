package com.hab.blog.feature.v1.user_center.Service;

import com.hab.blog.feature.v1.user_center.Dto.PrivacySettingsDto;
import com.hab.blog.feature.v1.user_center.Dto.UserProfileDto;
import com.hab.blog.feature.v1.user_center.Dto.UserProfileUpdateDto;
import com.hab.blog.feature.v1.auth.Dto.RelationshipStatus;
import com.hab.blog.feature.v1.entities.User.Gender;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.User.UserPrivacySettings;
import com.hab.blog.feature.v1.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
    // 更新用户资料的服务方法
    public void updateUserProfile(String username, UserProfileUpdateDto profileUpdateDto) {
        // 查找用户
        User user = userRepository.findUsersByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // 根据传入的 DTO 更新用户信息
        if (profileUpdateDto.getNickname() != null) {
            user.setNickname(profileUpdateDto.getNickname());
        }
        if (profileUpdateDto.getRelationshipStatus() != null) {
            user.setRelationshipStatus(RelationshipStatus.valueOf(profileUpdateDto.getRelationshipStatus()));
        }
        if (profileUpdateDto.getGender() != null) {
            user.setGender(Gender.valueOf(profileUpdateDto.getGender()));
        }
        if (profileUpdateDto.getBirthdate() != null) {
            user.setBirthdate(profileUpdateDto.getBirthdate());
        }
        if (profileUpdateDto.getBirthTime() != null) {
            user.setBirthTime(profileUpdateDto.getBirthTime());
        }
        if (profileUpdateDto.getBirthPlace() != null) {
            user.setBirthPlace(profileUpdateDto.getBirthPlace());
        }
        if (profileUpdateDto.getCurrentLocation() != null) {
            user.setCurrentLocation(profileUpdateDto.getCurrentLocation());
        }
        if (profileUpdateDto.getTimezone() != null) {
            user.setTimezone(profileUpdateDto.getTimezone());
        }
        if (profileUpdateDto.getDaylightSaving() != null) {
            user.setDaylightSaving(profileUpdateDto.getDaylightSaving());
        }
        if (profileUpdateDto.getZodiacSign() != null) {
            user.setZodiacSign(profileUpdateDto.getZodiacSign());
        }
        if (profileUpdateDto.getRisingSign() != null) {
            user.setRisingSign(profileUpdateDto.getRisingSign());
        }
        if (profileUpdateDto.getMoonSign() != null) {
            user.setMoonSign(profileUpdateDto.getMoonSign());
        }
        if (profileUpdateDto.getTarotPreference() != null) {
            user.setTarotPreference(profileUpdateDto.getTarotPreference());
        }
        // 保存更新后的用户
        userRepository.save(user);
    }
}
