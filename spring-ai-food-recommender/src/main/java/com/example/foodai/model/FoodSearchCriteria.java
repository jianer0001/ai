package com.example.foodai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 检索参数对象，由 AI 从自然语言中提取
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSearchCriteria {
    
    private String cuisineType;
    private Double maxPrice;
    private Double minRating;
    private Boolean isSpicy;
    private String flavorPreference;
    private Integer limit;
}
