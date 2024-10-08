package com.hab.blog.feature.v1.tarot.service;

import com.hab.blog.feature.v1.entities.tarot.TarotCard;
import com.hab.blog.feature.v1.entities.tarot.repository.TarotCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DailyTarotService {

    private static final String DAILY_TAROT_PREFIX = "daily_tarot:";

    @Autowired
    private TarotCardRepository tarotCardRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Optional<TarotCard> getDailyTarotForUser(String username) {
        String redisKey = DAILY_TAROT_PREFIX + username;

        // 1. 检查Redis中是否已有用户的今日塔罗牌
        String tarotCardId = redisTemplate.opsForValue().get(redisKey);
        if (tarotCardId != null) {
            // 如果Redis中有数据，返回对应塔罗牌
            Optional<TarotCard> tarotCard = tarotCardRepository.findById(Long.parseLong(tarotCardId));
            return tarotCard;
        }

        // 2. 从数据库中随机抽取一张塔罗牌
        List<TarotCard> allTarotCards = tarotCardRepository.findAll();
        if (allTarotCards.isEmpty()) {
            return Optional.empty();
        }

        TarotCard dailyCard = allTarotCards.get(new Random().nextInt(allTarotCards.size()));

        // 3. 将用户今日的塔罗牌存储到Redis，并设置24小时过期时间
        redisTemplate.opsForValue().set(redisKey, String.valueOf(dailyCard.getId()), Duration.ofHours(24));

        return Optional.of(dailyCard);
    }
}
