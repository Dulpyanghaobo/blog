package com.hab.blog.feature.v1.user_center.Service;

import com.hab.blog.feature.v1.entities.User.Repository.UserSettingsRepository;
import com.hab.blog.feature.v1.entities.User.UserSettings;
import com.hab.blog.feature.v1.user_center.dto.PrivacySettingsDto;
import com.hab.blog.feature.v1.user_center.dto.UserProfileDto;
import com.hab.blog.feature.v1.user_center.dto.UserProfileUpdateDto;
import com.hab.blog.feature.v1.auth.Dto.RelationshipStatus;
import com.hab.blog.feature.v1.entities.User.Gender;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.User.UserPrivacySettings;
import com.hab.blog.feature.v1.entities.repository.UserRepository;
import com.hab.blog.feature.v1.user_center.dto.UserSettingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    // 检查用户信息是否完整
    public boolean isProfileComplete(User user) {
        // 假设需要姓名和出生日期为必填项
        return user.getName() != null && user.getBirthdate() != null && user.getGender() != null;
    }

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
        boolean profileComplete = isProfileComplete(user);
        user.setProfileComplete(profileComplete);
        // 保存更新后的用户
        userRepository.save(user);
    }


    public UserSettingsDto getUserSettings(String username) {
        User user = userRepository.findUsersByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserSettings settings = user.getUserSettings();
        if (settings == null) {
            settings = createDefaultSettings(user);
            user.setUserSettings(settings);
            userSettingsRepository.save(settings);
        }
        return mapToDto(settings);
    }

    public void updateUserSettings(String username, UserSettingsDto settingsDto) {
        User user = userRepository.findUsersByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserSettings settings = user.getUserSettings();
        mapToEntity(settingsDto, settings);
        userSettingsRepository.save(settings);
    }

    // DTO -> Entity 映射
    private void mapToEntity(UserSettingsDto dto, UserSettings settings) {
        if (dto.getBackgroundMusic() != null) {
            settings.setBackgroundMusic(dto.getBackgroundMusic());
        }
        if (dto.getDailySignInReminder() != null) {
            settings.setDailySignInReminder(dto.getDailySignInReminder());
        }
        if (dto.getPersonalizedRecommendations() != null) {
            settings.setPersonalizedRecommendations(dto.getPersonalizedRecommendations());
        }
        if (dto.getFriendInteractionReminder() != null) {
            settings.setFriendInteractionReminder(dto.getFriendInteractionReminder());
        }
        if (dto.getDailyHoroscopeRecommendation() != null) {
            settings.setDailyHoroscopeRecommendation(dto.getDailyHoroscopeRecommendation());
        }
        if (dto.getPrivacyAllowProfileView() != null) {
            settings.setPrivacyAllowProfileView(dto.getPrivacyAllowProfileView());
        }
        if (dto.getAllowFriendRequests() != null) {
            settings.setAllowFriendRequests(dto.getAllowFriendRequests());
        }
        if (dto.getShareActivityStatus() != null) {
            settings.setShareActivityStatus(dto.getShareActivityStatus());
        }
        if (dto.getSystemNotifications() != null) {
            settings.setSystemNotifications(dto.getSystemNotifications());
        }
        if (dto.getEmailNotifications() != null) {
            settings.setEmailNotifications(dto.getEmailNotifications());
        }
        if (dto.getDarkMode() != null) {
            settings.setDarkMode(dto.getDarkMode());
        }
        if (dto.getFontSize() != null) {
            settings.setFontSize(dto.getFontSize());
        }
        if (dto.getLanguage() != null) {
            settings.setLanguage(dto.getLanguage());
        }
    }


    // Entity -> DTO 映射
    private UserSettingsDto mapToDto(UserSettings settings) {
        UserSettingsDto dto = new UserSettingsDto();
        dto.setBackgroundMusic(settings.getBackgroundMusic());
        dto.setDailySignInReminder(settings.getDailySignInReminder());
        dto.setPersonalizedRecommendations(settings.getPersonalizedRecommendations());
        dto.setFriendInteractionReminder(settings.getFriendInteractionReminder());
        dto.setDailyHoroscopeRecommendation(settings.getDailyHoroscopeRecommendation());
        dto.setPrivacyAllowProfileView(settings.getPrivacyAllowProfileView());
        dto.setAllowFriendRequests(settings.getAllowFriendRequests());
        dto.setShareActivityStatus(settings.getShareActivityStatus());
        dto.setSystemNotifications(settings.getSystemNotifications());
        dto.setEmailNotifications(settings.getEmailNotifications());
        dto.setDarkMode(settings.getDarkMode());
        dto.setFontSize(settings.getFontSize());
        dto.setLanguage(settings.getLanguage());
        return dto;
    }


    // 创建默认的用户设置
    private UserSettings createDefaultSettings(User user) {
        UserSettings settings = new UserSettings();
        settings.setUser(user);
        settings.setBackgroundMusic(false);
        settings.setDailySignInReminder(false);
        settings.setPersonalizedRecommendations(false);
        settings.setFriendInteractionReminder(false);
        settings.setDailyHoroscopeRecommendation(false);
        settings.setPrivacyAllowProfileView(true);
        settings.setAllowFriendRequests(true);
        settings.setShareActivityStatus(true);
        settings.setSystemNotifications(true);
        settings.setEmailNotifications(true);
        settings.setDarkMode(false);
        settings.setFontSize("medium");
        settings.setLanguage("en");
        return settings;
    }

    // 检查用户是否完成新手教程
    public boolean hasCompletedTutorial(String username) {
        User user = userRepository.findUsersByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getHasCompletedTutorial();
    }

    // 标记用户完成新手教程
    public void completeTutorial(String username) {
        User user = userRepository.findUsersByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setHasCompletedTutorial(true);
        userRepository.save(user);
    }
}
