package com.hab.blog.common.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // 逻辑判断服务是否健康
        boolean serviceUp = checkExternalService();
        if (serviceUp) {
            return Health.up().withDetail("My Service", "Available").build();
        }
        return Health.down().withDetail("My Service", "Not Available").build();
    }

    private boolean checkExternalService() {
        // 检查外部服务的逻辑
        return true; // 这里假设服务可用
    }
}

