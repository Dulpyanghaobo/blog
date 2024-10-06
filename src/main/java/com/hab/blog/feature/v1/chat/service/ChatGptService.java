package com.hab.blog.feature.v1.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {
    private final WebClient webClient;

    @Value("${chat.chatGPT.apiKey}")
    private String API_KEY;
    public ChatGptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/").build();
    }

    public Mono<String> callChatGpt(String prompt) {
        return webClient.post()
                .uri("completions")
                .header("Authorization", "Bearer " + API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(buildRequestBody(prompt)))
                .retrieve()
                .bodyToMono(String.class);
    }

    private Map<String, Object> buildRequestBody(String prompt) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini"); // 选择模型
        body.put("prompt", prompt);
        body.put("max_tokens", 1000); // 输出的最大token数
        return body;
    }
}
