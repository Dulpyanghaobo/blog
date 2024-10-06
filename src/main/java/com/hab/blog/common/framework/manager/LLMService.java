package com.hab.blog.common.framework.manager;

import java.util.concurrent.CompletableFuture;

public interface LLMService {
    String callModelSync(String prompt);

    CompletableFuture<String> callModelAsync(String prompt);
}
