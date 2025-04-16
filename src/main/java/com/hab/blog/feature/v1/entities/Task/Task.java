package com.hab.blog.feature.v1.entities.Task;

import com.hab.blog.feature.v1.entities.Objective.Objective;
import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task {
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

    private String status = "Not Started";

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;

    private int complexity = 1;
    private int priority = 1;
    private int importance = 1;
}

