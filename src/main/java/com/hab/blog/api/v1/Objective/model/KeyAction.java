package com.hab.blog.api.v1.Objective.model;

import com.hab.blog.api.v1.Objective.Objective;
import com.hab.blog.api.v1.auth.Entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "key_action")
@Data
@NoArgsConstructor
public class KeyAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private Objective objective;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private Date startDate;
    private Date endDate;
    private double progress = 0;
    private String status = "Not Started";

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int complexity = 1;
    private int priority = 1;
    private int importance = 1;
}
