package com.sutalk.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 👇 윈도우 기준 절대 경로 (C:/Users/pmsoo/IdeaProjects/sutalk-backend/uploads)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/pmsoo/IdeaProjects/sutalk-backend/uploads/");
    }
}
