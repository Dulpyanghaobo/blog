package com.hab.blog.dto;

import lombok.Data;

@Data
public class PostDto {
    private long userId;
    private String title;
    private String slug;
    private String releaseSnapshot;
    private String headSnapshot;
    private String baseSnapshot;
    private String owner;
    private String template;
    private String cover;
    private Boolean deleted;
    private Boolean publish;
    private Boolean pinned;
    private Boolean allowComment;
    private String visible;
    private Integer priority;
    private String excerpt;
    private String categories;
    private String tags;
    private String htmlMetas;
}
