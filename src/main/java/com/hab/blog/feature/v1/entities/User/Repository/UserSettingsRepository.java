package com.hab.blog.feature.v1.entities.User.Repository;

import com.hab.blog.feature.v1.entities.User.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long>  {
}
