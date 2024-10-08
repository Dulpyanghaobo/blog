package com.hab.blog.feature.v1.app_config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app")
public class AppConfigController {

    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getAppConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("latestVersion", "1.2.3");
        config.put("updateRequired", false);
        config.put("adsEnabled", true);

        Map<String, Boolean> features = new HashMap<>();
        features.put("newFeatureEnabled", true);
        features.put("betaFeatureEnabled", false);
        config.put("features", features);

        return ResponseEntity.ok(config);
    }
}

