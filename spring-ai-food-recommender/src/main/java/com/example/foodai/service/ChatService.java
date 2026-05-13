package com.example.foodai.service;

import com.example.foodai.model.FoodSearchResult;
import com.example.foodai.retriever.FoodRetrievalTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 智能推荐服务，整合 AI 对话和数据库检索
 */
@Slf4j
@Service
public class ChatService {
    
    private final ChatClient chatClient;
    private final FoodRetrievalTools foodRetrievalTools;
    
    public ChatService(ChatClient.Builder chatClientBuilder, 
                       FoodRetrievalTools foodRetrievalTools) {
        this.chatClient = chatClientBuilder.build();
        this.foodRetrievalTools = foodRetrievalTools;
    }
    
    /**
     * 处理用户查询，结合 AI 意图识别和数据库检索生成推荐
     */
    public String getRecommendation(String userQuery) {
        log.info("Processing user query: {}", userQuery);
        
        String systemPrompt = """
            你是一个智能美食推荐助手。你有能力访问餐厅和菜品数据库来提供个性化推荐。
            
            当用户询问食物推荐时：
            1. 分析用户的偏好（菜系、价格、口味等）
            2. 使用 searchFood 工具查询数据库获取匹配的餐厅和菜品
            3. 基于查询结果，生成友好、详细的推荐回复
            4. 推荐理由要具体，提及菜品特色、店铺评分等信息
            5. 如果没有找到完全匹配的结果，提供相似的替代方案
            
            请用中文回复，语气友好专业。
            """;
        
        try {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userQuery)
                .tools(foodRetrievalTools)
                .call()
                .content();
            
            log.info("Generated recommendation: {}", response);
            return response;
            
        } catch (Exception e) {
            log.error("Error generating recommendation", e);
            return "抱歉，暂时无法为您提供推荐，请稍后再试。";
        }
    }
    
    /**
     * 简单对话模式（不使用工具）
     */
    public String chat(String userMessage) {
        return chatClient.prompt()
            .user(userMessage)
            .call()
            .content();
    }
}
