package com.hab.blog.feature.v1.entities.Mood;

import com.hab.blog.feature.v1.entities.User.User;
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
    @JoinColumn(name="user_name", nullable=false)
    private User user;

    @Column(name="mood_date", nullable = false)
    private LocalDate date;

    @Column(name="mood_level", nullable=false)
    private Integer moodLevel;

    //constructors, getters and setters
}