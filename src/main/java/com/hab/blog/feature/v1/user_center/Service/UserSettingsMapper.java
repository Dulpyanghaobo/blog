package com.hab.blog.feature.v1.user_center.Service;

import com.hab.blog.feature.v1.entities.User.UserSettings;
import com.hab.blog.feature.v1.user_center.dto.UserSettingsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserSettingsMapper {
    // 将 DTO 映射到实体
    void updateUserSettingsFromDto(UserSettingsDto dto, @MappingTarget UserSettings settings);
}

