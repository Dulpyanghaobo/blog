package com.hab.blog.feature.v1.entities.User;

import com.hab.blog.feature.v1.auth.Dto.RelationshipStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String avatar;               // 用户头像
    private String bio;                  // 用户简介
    private boolean disabled;            // 用户是否被禁用

    @Column(unique = true, nullable = false)
    private String email;                // 用户邮箱

    @Column(nullable = false)
    private String password;             // 用户密码

    private String phoneNumber;          // 用户手机号

    @Column(nullable = true)
    private Instant registeredAt;        // 用户注册时间

    private boolean twoFactorAuthEnabled; // 是否启用双重认证

    @Column(nullable = false)
    private String userName;             // 用户名

    @Column(nullable = false)
    private String name;             // 用户昵称

    @Column(unique = true)
    private String openid;               // 微信用户的唯一标识

    // 用户角色（已存在）
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();  // 用户角色

    // 用户绑定的外部账户
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserLinkedAccount> linkedAccounts = new HashSet<>();

    // 用户隐私设置
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPrivacySettings privacySettings;

    @Column(length = 100)
    private String nickname;             // 用户昵称

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus; // 用户关系状态 (单身、恋爱中、已婚等)

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;               // 用户性别 (男性、女性、其他)

    private LocalDate birthdate;         // 出生日期
    private LocalTime birthTime;         // 出生时间
    private String birthPlace;           // 出生地点
    private String currentLocation;      // 当前居住地点
    private String timezone;             // 时区
    private Boolean daylightSaving;      // 是否启用夏令时

    private String zodiacSign;           // 星座
    private String risingSign;           // 上升星座
    private String moonSign;             // 月亮星座

    private String tarotPreference;      // 用户喜欢的大阿卡那牌
    @ElementCollection
    private List<String> interests;      // 用户的兴趣标签

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFriend> friends = new HashSet<>();

    // 新增的出生地经纬度字段
    @Column(name = "birth_location_lat", precision = 9, scale = 6)
    private BigDecimal birthLocationLat;

    @Column(name = "birth_location_lng", precision = 9, scale = 6)
    private BigDecimal birthLocationLng;
}
