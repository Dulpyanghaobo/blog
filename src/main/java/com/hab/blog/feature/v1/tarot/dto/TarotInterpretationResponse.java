package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;

import java.util.List;

@Data
public class TarotInterpretationResponse {

    private List<TarotCardInterpretation> cardInterpretations;
    private String overallReading;

    // Getters and Setters
}
