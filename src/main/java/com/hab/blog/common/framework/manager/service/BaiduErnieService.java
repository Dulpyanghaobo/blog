package com.hab.blog.common.framework.manager.service;
import com.hab.blog.common.framework.manager.LLMService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class BaiduErnieService implements LLMService {

    private final WebClient webClient;

    public BaiduErnieService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://wenxin.baidu.com/v1/").build();
    }

    @Override
    public String callModelSync(String prompt) {
        // 同步调用百度文心大模型
        return webClient.post()
                .uri("ernie/completions")
                .header("Authorization", "Bearer " + System.getenv("BAIDU_API_KEY"))
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .block();  // 同步阻塞获取结果
    }

    @Override
    public CompletableFuture<String> callModelAsync(String prompt) {
        // 异步调用百度文心大模型
        return webClient.post()
                .uri("ernie/completions")
                .header("Authorization", "Bearer " + System.getenv("BAIDU_API_KEY"))
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();  // 返回异步CompletableFuture
    }

    private Map<String, Object> buildRequestBody(String prompt) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "ernie-bot");
        body.put("prompt", prompt);
        body.put("max_tokens", 1000);
        return body;
    }
}
