package com.hab.blog.feature.v1.home.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserAstrologyResponse {
    private Long id;  // UserAstrology ID
    private String personalInterpretation;
    private LocalDate createdAt;

    // Astrology event details
    private Long astrologyEventId;
    private LocalDate eventDate;
    private String planetPosition;
    private String zodiacChange;
    private String eventDescription;
    public UserAstrologyResponse(Long id, String personalInterpretation, LocalDate createdAt,
                                 Long astrologyEventId, LocalDate eventDate,
                                 String planetPosition, String zodiacChange, String eventDescription) {
        this.id = id;
        this.personalInterpretation = personalInterpretation;
        this.createdAt = createdAt;
        this.astrologyEventId = astrologyEventId;
        this.eventDate = eventDate;
        this.planetPosition = planetPosition;
        this.zodiacChange = zodiacChange;
        this.eventDescription = eventDescription;
    }
}
