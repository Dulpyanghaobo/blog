package com.hab.blog.feature.v1.moods.Entity;

import com.hab.blog.feature.v1.auth.Entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "mood")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="mood_date", nullable = false)
    private LocalDate date;

    @Column(name="mood_level", nullable=false)
    private Integer moodLevel;

    //constructors, getters and setters
}