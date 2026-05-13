package com.example.foodai.retriever;

import com.example.foodai.model.FoodSearchCriteria;
import com.example.foodai.model.FoodSearchResult;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * AI 工具类，将检索服务暴露为 Function Calling 工具
 */
@Component
public class FoodRetrievalTools {
    
    private final FoodRetrievalService retrievalService;
    
    public FoodRetrievalTools(FoodRetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }
    
    @Tool(description = "根据用户偏好搜索餐厅和菜品。可用于查找特定菜系、价格范围、口味偏好的食物推荐。")
    public FoodSearchResult searchFood(
        @ToolParam(description = "菜系类型，如'川菜'、'粤菜'、'日本料理'等") String cuisineType,
        @ToolParam(description = "最高价格限制（人均消费）") Double maxPrice,
        @ToolParam(description = "最低评分要求（1-5 分）") Double minRating,
        @ToolParam(description = "是否要辣味食物") Boolean isSpicy,
        @ToolParam(description = "口味偏好描述，如'清淡'、'重口味'、'酸甜'等") String flavorPreference,
        @ToolParam(description = "返回结果数量限制") Integer limit
    ) {
        FoodSearchCriteria criteria = FoodSearchCriteria.builder()
            .cuisineType(cuisineType)
            .maxPrice(maxPrice)
            .minRating(minRating)
            .isSpicy(isSpicy)
            .flavorPreference(flavorPreference)
            .limit(limit != null ? limit : 10)
            .build();
        
        return retrievalService.search(criteria);
    }
}
