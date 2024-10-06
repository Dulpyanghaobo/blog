package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;

@Data
public class TarotCardInterpretation {

    private String cardName;
    private String position;
    private String interpretation;

    public TarotCardInterpretation(String cardName, String position, String interpretation) {
        this.cardName = cardName;
        this.position = position;
        this.interpretation = interpretation;
    }

    // Getters and Setters
}
