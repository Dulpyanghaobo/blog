package com.hab.blog.feature.v1.entities.tarot;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarot_categories")
@Data
public class TarotCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String description;
}

