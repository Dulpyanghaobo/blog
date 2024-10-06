package com.hab.blog.feature.v1.entities.carot;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tarot_cards")
public class TarotCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true)
    private Integer number;

    @Column(name = "arcana_type", nullable = false, length = 50)
    private String arcanaType;

    @Column(nullable = true, length = 50)
    private String suit;

    @Column(nullable = true, length = 255)
    private String archetype;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String attributes;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String symbolism;

    @Column(name = "upright_meaning", nullable = true, columnDefinition = "TEXT")
    private String uprightMeaning;

    @Column(name = "reversed_meaning", nullable = true, columnDefinition = "TEXT")
    private String reversedMeaning;

    @Column(name = "upright_advice", nullable = true, columnDefinition = "TEXT")
    private String uprightAdvice;

    @Column(name = "reversed_advice", nullable = true, columnDefinition = "TEXT")
    private String reversedAdvice;

    @Column(name = "image_url", nullable = true, length = 255)
    private String imageUrl;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String questions;

    @Column(name = "astrological_sign", nullable = true, length = 50)
    private String astrologicalSign;

    @Column(nullable = true, length = 50)
    private String element;

    @Column(nullable = true, length = 50)
    private String chakra;

    @Column(nullable = true, length = 50)
    private String numerology;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String keywords;
}
