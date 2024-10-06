package com.hab.blog.common.framework.manager;


import com.hab.blog.common.framework.manager.router.ModelRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TaskSchedulerService {

    @Autowired
    private ModelRouter modelRouter;

    /**
     * 异步执行大模型任务
     * @param prompt 用户输入的提示词
     * @return CompletableFuture 包装的异步结果
     */
    @Async
    public CompletableFuture<String> executeAsyncTask(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            // 获取可用的大模型并同步调用
            LLMService modelService = modelRouter.routeToAvailableModel();
            return modelService.callModelSync(prompt);
        });
    }

    /**
     * 同步执行大模型任务
     * @param prompt 用户输入的提示词
     * @return 同步结果
     */
    public String executeSyncTask(String prompt) {
        LLMService modelService = modelRouter.routeToAvailableModel();
        return modelService.callModelSync(prompt);
    }

    /**
     * 异步任务的结果处理，可以进行额外操作或回调
     * @param prompt 用户输入的提示词
     * @return Future 包装的异步结果，带有回调机制
     */
    @Async
    public Future<String> executeAsyncTaskWithCallback(String prompt) {
        try {
            LLMService modelService = modelRouter.routeToAvailableModel();
            String result = modelService.callModelSync(prompt);
            // 模拟异步完成后的其他逻辑，比如将结果保存到数据库
            System.out.println("Task completed successfully. Result: " + result);
            return new AsyncResult<>(result);
        } catch (Exception e) {
            // 异常处理
            System.err.println("Error occurred while executing async task: " + e.getMessage());
            return new AsyncResult<>(null);
        }
    }
}
