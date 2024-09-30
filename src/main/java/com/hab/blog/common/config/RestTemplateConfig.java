package com.hab.blog.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 创建 MappingJackson2HttpMessageConverter 并为其添加 text/plain 支持
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN); // 添加 text/plain 支持
        mediaTypes.add(MediaType.APPLICATION_JSON); // 添加 application/json 支持
        converter.setSupportedMediaTypes(mediaTypes);

        // 将自定义的 converter 添加到 RestTemplate
        restTemplate.getMessageConverters().add(converter);

        return restTemplate;
    }
}

