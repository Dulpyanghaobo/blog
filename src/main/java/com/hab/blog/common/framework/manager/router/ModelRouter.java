package com.hab.blog.common.framework.manager.router;

import com.hab.blog.common.framework.manager.LLMService;
import com.hab.blog.common.framework.manager.ModelManager;
import com.hab.blog.common.framework.manager.ModelUnavailableException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelRouter {
    private final ModelManager modelManager;
    private final List<String> modelOrder; // 按优先级排列的模型列表

    public ModelRouter(ModelManager modelManager, List<String> modelOrder) {
        this.modelManager = modelManager;
        this.modelOrder = modelOrder;
    }

    public LLMService routeToAvailableModel() {
        for (String modelName : modelOrder) {
            try {
                return modelManager.getModel(modelName);
            } catch (ModelUnavailableException e) {
                // 继续尝试下一个模型
            }
        }
        throw new RuntimeException("No available models.");
    }
}

