package com.hab.blog.feature.v1.entities.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_privacy_settings")
@Data
@NoArgsConstructor
public class UserPrivacySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;

    private boolean shareBirthdate;   // 是否分享生日
    private boolean shareGender;      // 是否分享性别
    private boolean shareLocation;    // 是否分享位置
}
