package com.hab.blog.feature.v1.tarot.dto;

import com.hab.blog.feature.v1.entities.carot.TarotCard;
import lombok.Data;

import java.util.List;

@Data
public class TarotInterpretationRequest {

    private List<TarotCardRequest> tarotCards;
    private String tarotSpreadsType;
    private String questions;
}