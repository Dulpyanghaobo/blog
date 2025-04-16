package com.hab.blog.feature.v1.entities.tarot;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_horoscope")
@Data
public class DailyHoroscope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarot_card_id")  // 关联到塔罗牌表
    private TarotCard tarotCard;

    @Column(name = "horoscope_text", columnDefinition = "TEXT")  // 运势解读结果
    private String horoscopeText;

    @Column(name = "horoscope_date", nullable = false)  // 运势日期
    private LocalDate horoscopeDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 无参构造函数
    public DailyHoroscope() {
        this.createdAt = LocalDateTime.now();  // 默认创建时间
    }
}

