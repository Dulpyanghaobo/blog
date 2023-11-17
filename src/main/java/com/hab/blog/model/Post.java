package com.hab.blog.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Post到User的多对一关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // 对应数据库中的外键列
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    // 可能需要将此字段与文件存储服务链接，这里只存储引用
    @Column(name = "release_snapshot")
    private String releaseSnapshot;

    // 头快照，如有需要可以加入
    @Column(name = "head_snapshot")
    private String headSnapshot;

    // 基础快照，如有需要可以加入
    @Column(name = "base_snapshot")
    private String baseSnapshot;

    // 文章的拥有者，可能是用户的ID或用户名
    @Column(name = "owner")
    private String owner;

    // 使用的模板
    @Column(name = "template")
    private String template;

    // 封面图片路径
    @Column(name = "cover")
    private String cover;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false)
    private Boolean publish = false;

    // 发布时间可以为空，这表示文章还没有发布
    @Column(name = "publish_time")
    private Instant publishTime;

    @Column(nullable = false)
    private Boolean pinned = false;

    @Column(nullable = false)
    private Boolean allowComment = true;

    // 使用枚举来限制值
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisibleEnum visible = VisibleEnum.PUBLIC;

    @Column(nullable = false)
    private Integer priority = 0;

    // 文章摘要，如有需要可以映射为一个文本字段
    @Column(name = "excerpt")
    private String excerpt;

    // 假设分类和标签以逗号分隔的字符串存储，或者可以创建单独的表来映射多对多关系
    @Column(name = "categories")
    private String categories;

    @Column(name = "tags")
    private String tags;

    // HTML元数据可能需要复杂的映射，这里简化为一个文本字段，具体取决于需求
    @Column(name = "html_metas")
    private String htmlMetas;

    // You can add more fields, constructors, and methods as needed.
}

// 可见性枚举，与原类一致
enum VisibleEnum {
    PUBLIC,
    INTERNAL,
    PRIVATE
}