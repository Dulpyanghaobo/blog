package com.hab.blog.common.framework.manager.service;

import com.hab.blog.common.framework.manager.LLMService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class OpenAIGptService implements LLMService {
    private final WebClient webClient;

    @Value("${chat.chatGPT.apiKey}")
    private String API_KEY;
    public OpenAIGptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/").build();
    }

    @Override
    public String callModelSync(String prompt) {
        // 同步调用OpenAI GPT
        return webClient.post()
                .uri("completions")
                .header("Authorization", "Bearer " + API_KEY)
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class)  // 获取结果的字符串形式
                .block();  // 同步阻塞获取结果
    }

    @Override
    public CompletableFuture<String> callModelAsync(String prompt) {
        // 异步调用OpenAI GPT
        return webClient.post()
                .uri("completions")
                .header("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();  // 返回异步CompletableFuture
    }

    private Map<String, Object> buildRequestBody(String prompt) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");  // 可以根据需要选择模型
        body.put("prompt", prompt);
        body.put("max_tokens", 1000);  // 控制输出的最大 token 数
        return body;
    }
}
