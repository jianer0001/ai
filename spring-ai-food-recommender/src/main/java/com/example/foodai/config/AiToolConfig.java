package com.example.foodai.config;

import com.example.foodai.retriever.FoodRetrievalService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * AI 工具配置
 */
@Configuration
public class AiToolConfig {
    
    /**
     * 配置工具回调提供者，使 AI 能够调用 FoodRetrievalTools 中的方法
     */
    @Bean
    @Primary
    public ToolCallbackProvider foodToolCallbackProvider() {
        return MethodToolCallbackProvider.builder()
            .toolObjects("foodRetrievalTools")
            .build();
    }
}
