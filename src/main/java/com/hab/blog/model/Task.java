package com.hab.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor // Lombok annotation to create a no-args constructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskMode mode;

    // Post到User的多对一关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // 对应数据库中的外键列
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "okr_id", nullable = true) // This column will store the ID of the OKR
    private OKR okr;
    @ElementCollection
    private List<String> tags;

    @Column(nullable = false)
    private int difficulty;

    @Column(nullable = false)
    private int urgency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private Instant createAt = Instant.now();

    @Column(nullable = false)
    private Instant updateAt = Instant.now();

    @Column
    private Instant deadline;

    @Column(nullable = false)
    private int importance;

    @Column(nullable = false)
    private int progress; // Progress as a percentage (0-100)

    @Column(length = 1000)
    private String comments;
}

@Getter
enum TaskCategory {
    STUDY("学习"),
    WORK("工作"),
    LIFE("生活"),
    TECHNOLOGY("技术"),
    THINKING("思考");

    private final String displayName;

    TaskCategory(String displayName) {
        this.displayName = displayName;
    }

}

@Getter
enum TaskMode {
    PERMANENT("常驻任务"),
    REPETITIVE("重复性任务"),
    ONCE("一次性任务");

    private final String displayName;

    TaskMode(String displayName) {
        this.displayName = displayName;
    }

}

@Getter
enum TaskStatus {
    CREATED("创建"),
    IN_PROGRESS("执行中"),
    IS_BLOCK("阻塞"),
    WAITING("等待"),
    COMPLETED("完成"),
    SETTLEMENT("结算");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

}
