package com.example.foodai.controller;

import com.example.foodai.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天推荐控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    
    @Data
    public static class ChatRequest {
        private String message;
    }
    
    @Data
    public static class ChatResponse {
        private String reply;
        private boolean isRecommendation;
    }
    
    /**
     * 获取智能美食推荐
     * POST /api/chat/recommend
     */
    @PostMapping("/recommend")
    public ChatResponse getRecommendation(@RequestBody ChatRequest request) {
        log.info("Received recommendation request: {}", request.getMessage());
        
        String recommendation = chatService.getRecommendation(request.getMessage());
        
        ChatResponse response = new ChatResponse();
        response.setReply(recommendation);
        response.setRecommendation(true);
        
        return response;
    }
    
    /**
     * 普通对话
     * POST /api/chat/message
     */
    @PostMapping("/message")
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        log.info("Received message: {}", request.getMessage());
        
        String reply = chatService.chat(request.getMessage());
        
        ChatResponse response = new ChatResponse();
        response.setReply(reply);
        response.setRecommendation(false);
        
        return response;
    }
    
    /**
     * GET 方式的快捷推荐接口
     * GET /api/chat/recommend?query=xxx
     */
    @GetMapping("/recommend")
    public ChatResponse getRecommendationGet(@RequestParam String query) {
        log.info("Received recommendation query: {}", query);
        
        String recommendation = chatService.getRecommendation(query);
        
        ChatResponse response = new ChatResponse();
        response.setReply(recommendation);
        response.setRecommendation(true);
        
        return response;
    }
}
