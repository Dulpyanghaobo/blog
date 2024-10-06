package com.hab.blog.common.framework.manager;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ModelFusionService {

    public String fuseResults(List<String> results) {
        // 例如，简单的拼接结果
        return String.join(" ", results);
    }

    public String weightedFusion(Map<String, Integer> weightedResults) {
        // 根据权重合并结果
        return weightedResults.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("");
    }
}
