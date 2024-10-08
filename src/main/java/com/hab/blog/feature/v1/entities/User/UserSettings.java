package com.hab.blog.feature.v1.entities.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_settings")
@Data
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "background_music", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean backgroundMusic = false;

    @Column(name = "daily_sign_in_reminder", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean dailySignInReminder = false;

    @Column(name = "personalized_recommendations", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean personalizedRecommendations = false;

    @Column(name = "friend_interaction_reminder", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean friendInteractionReminder = false;

    @Column(name = "daily_horoscope_recommendation", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean dailyHoroscopeRecommendation = false;

    @Column(name = "privacy_allow_profile_view", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean privacyAllowProfileView = true;

    @Column(name = "allow_friend_requests", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean allowFriendRequests = true;

    @Column(name = "share_activity_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean shareActivityStatus = true;

    @Column(name = "system_notifications", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean systemNotifications = true;

    @Column(name = "email_notifications", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean emailNotifications = true;

    @Column(name = "dark_mode", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean darkMode = false;

    @Column(name = "font_size", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'medium'")
    private String fontSize = "medium";  // 可选择 'small', 'medium', 'large'

    @Column(name = "language", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'en'")
    private String language = "en";  // 默认语言为英语
}

