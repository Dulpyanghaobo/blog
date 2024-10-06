package com.hab.blog.common.framework.manager;

import com.hab.blog.common.framework.manager.service.BaiduErnieService;
import com.hab.blog.common.framework.manager.service.OpenAIGptService;
import com.hab.blog.common.framework.manager.service.PerplexityGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ModelManager {
    private Map<String, LLMService> models = new ConcurrentHashMap<>();
    private Map<String, Boolean> modelHealthStatus = new ConcurrentHashMap<>();
    @Autowired

    public ModelManager(OpenAIGptService openAIGptService, BaiduErnieService baiduErnieService, PerplexityGptService perplexityGptService) {
        // 注册模型
        registerModel("OpenAI-GPT", openAIGptService);
        registerModel("Baidu-Ernie", baiduErnieService);
        registerModel("Perplexity-GPT", perplexityGptService);
    }
    public void registerModel(String modelName, LLMService modelService) {
        models.put(modelName, modelService);
        modelHealthStatus.put(modelName, true); // 初始健康状态为true
    }

    public LLMService getModel(String modelName) {
        if (modelHealthStatus.getOrDefault(modelName, false)) {
            return models.get(modelName);
        } else {
            throw new ModelUnavailableException(modelName + " is unavailable.");
        }
    }

    public void setModelHealth(String modelName, boolean isHealthy) {
        modelHealthStatus.put(modelName, isHealthy);
    }
}
