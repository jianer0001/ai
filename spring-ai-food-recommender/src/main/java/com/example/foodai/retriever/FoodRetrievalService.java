package com.example.foodai.retriever;

import com.example.foodai.model.FoodSearchCriteria;
import com.example.foodai.model.FoodSearchResult;

/**
 * 统一的食物检索接口，支持不同实现策略（SQL、向量等）
 * 采用策略模式，便于后续无缝切换到向量数据库
 */
public interface FoodRetrievalService {
    
    /**
     * 根据搜索条件检索食物和店铺信息
     * @param criteria 搜索条件
     * @return 检索结果
     */
    FoodSearchResult search(FoodSearchCriteria criteria);
    
    /**
     * 获取服务名称，用于标识实现策略
     */
    String getServiceName();
}
