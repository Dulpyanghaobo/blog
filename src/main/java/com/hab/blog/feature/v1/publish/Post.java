package com.hab.blog.feature.v1.publish;


import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean allowComment;
    private String baseSnapshot;
    private String categories;
    private String cover;
    private boolean deleted;
    private String excerpt;
    private String headSnapshot;
    private String htmlMetas;
    private String owner;
    private boolean pinned;
    private int priority;
    private boolean publish;
    private Instant publishTime;
    private String releaseSnapshot;

    @Column(unique = true, nullable = false)
    private String slug;

    private String tags;
    private String template;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visible visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum Visible {
        INTERNAL, PRIVATE, PUBLIC
    }
}
