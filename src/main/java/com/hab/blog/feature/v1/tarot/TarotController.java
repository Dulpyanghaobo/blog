package com.hab.blog.feature.v1.tarot;

import com.hab.blog.feature.v1.entities.tarot.TarotCategory;
import com.hab.blog.feature.v1.tarot.dto.*;
import com.hab.blog.feature.v1.tarot.service.TarotCardService;
import com.hab.blog.feature.v1.tarot.service.TarotReadingService;
import com.hab.blog.feature.v1.tarot.service.TarotSpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tarot")
public class TarotController {

    @Autowired
    private TarotCardService tarotCardService;
    @Autowired
    private TarotSpreadService tarotSpreadService;
    @Autowired
    private TarotReadingService tarotReadingService;
    // 获取所有塔罗牌阵
    @GetMapping("/spreads")
    public ResponseEntity<List<TarotSpreadResponse>> getAllTarotSpreads() {
        List<TarotSpreadResponse> spreads = tarotSpreadService.getAllTarotSpreads();
        return ResponseEntity.ok(spreads);
    }

    // 根据ID获取塔罗牌阵详情
    @GetMapping("/spreads/{id}")
    public ResponseEntity<TarotSpreadResponse> getTarotSpreadById(@PathVariable Long id) {
        TarotSpreadResponse spread = tarotSpreadService.getTarotSpreadById(id);
        if (spread != null) {
            return ResponseEntity.ok(spread);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/draw")
    public List<TarotCardResponse> drawTarotCards(@RequestBody TarotDrawRequest request) {
        return tarotCardService.drawTarotCards(request);
    }
    @PostMapping("/interpret")
    public ResponseEntity<TarotInterpretationResponse> interpretTarotReading(@RequestBody TarotInterpretationRequest request) {
        TarotInterpretationResponse response = tarotReadingService.generateTarotInterpretation(request);
        return ResponseEntity.ok(response);
    }

    // 新增接口: 系统猜测用户想问的问题或推荐解读
    @GetMapping("/guess")
    public ResponseEntity<List<String>> guessUserQuestion() {
        List<String> guessResponse = tarotReadingService.guessUserQuestion();
        return ResponseEntity.ok(guessResponse);
    }

    // 获取所有塔罗种类
    @GetMapping("/categories")
    public ResponseEntity<List<TarotCategory>> getAllTarotCategories() {
        List<TarotCategory> categories = tarotReadingService.getAllTarotCategories();
        return ResponseEntity.ok(categories);
    }


}
