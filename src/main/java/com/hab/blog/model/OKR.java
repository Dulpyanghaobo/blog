package com.hab.blog.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "okrs")
public class OKR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objective;

    @ElementCollection
    private List<String> keyResults;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String timeFrame;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "okr", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @Column
    private int priority;

    @Column(length = 1000)
    private String comments;

    @Column(nullable = false)
    private int progress; // Progress as a percentage (0-100)


    // Getters and setters omitted for brevity
}
