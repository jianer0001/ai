package com.example.foodai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 统一的检索结果对象，屏蔽底层实现细节（SQL 或向量）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSearchResult {
    
    private List<ShopInfo> shops;
    private List<DishInfo> dishes;
    private String searchSummary;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopInfo {
        private Long id;
        private String name;
        private String cuisineType;
        private String flavorTags;
        private BigDecimal avgPrice;
        private String address;
        private BigDecimal rating;
        private String description;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DishInfo {
        private Long id;
        private String name;
        private Long shopId;
        private String shopName;
        private BigDecimal price;
        private String description;
        private String flavorTags;
        private Boolean isSpicy;
    }
}
