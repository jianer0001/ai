package com.example.foodai.retriever;

import com.example.foodai.model.Dish;
import com.example.foodai.model.FoodSearchCriteria;
import com.example.foodai.model.FoodSearchResult;
import com.example.foodai.model.Shop;
import com.example.foodai.repository.DishRepository;
import com.example.foodai.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于 JPA/SQL 的食物检索实现
 * 当前默认策略，后续可无缝切换到向量数据库实现
 */
@Slf4j
@Service("jpaFoodRetrievalService")
public class JpaFoodRetrievalService implements FoodRetrievalService {
    
    private final ShopRepository shopRepository;
    private final DishRepository dishRepository;
    
    public JpaFoodRetrievalService(ShopRepository shopRepository, 
                                   DishRepository dishRepository) {
        this.shopRepository = shopRepository;
        this.dishRepository = dishRepository;
    }
    
    @Override
    public FoodSearchResult search(FoodSearchCriteria criteria) {
        log.info("Searching with criteria: {}", criteria);
        
        // 查询店铺
        List<Shop> shops = shopRepository.findShopsByCriteria(
            criteria.getCuisineType(),
            criteria.getMaxPrice(),
            criteria.getMinRating()
        );
        
        // 查询菜品 - 优先使用带口味偏好的查询
        List<Dish> dishes;
        if (criteria.getFlavorPreference() != null && !criteria.getFlavorPreference().isEmpty()) {
            dishes = dishRepository.findDishesWithFlavorPreference(
                criteria.getCuisineType(),
                criteria.getMaxPrice(),
                criteria.getIsSpicy(),
                criteria.getFlavorPreference()
            );
        } else {
            dishes = dishRepository.findDishesWithShopCriteria(
                criteria.getCuisineType(),
                criteria.getMaxPrice(),
                criteria.getIsSpicy()
            );
        }
        
        // 转换为统一结果对象
        List<FoodSearchResult.ShopInfo> shopInfos = shops.stream()
            .map(this::toShopInfo)
            .collect(Collectors.toList());
        
        List<FoodSearchResult.DishInfo> dishInfos = dishes.stream()
            .map(this::toDishInfo)
            .collect(Collectors.toList());
        
        String summary = String.format("找到 %d 家店铺和 %d 道菜品", 
            shopInfos.size(), dishInfos.size());
        
        log.info("Search completed: {}", summary);
        
        return FoodSearchResult.builder()
            .shops(shopInfos)
            .dishes(dishInfos)
            .searchSummary(summary)
            .build();
    }
    
    @Override
    public String getServiceName() {
        return "JPA_SQL";
    }
    
    private FoodSearchResult.ShopInfo toShopInfo(Shop shop) {
        return FoodSearchResult.ShopInfo.builder()
            .id(shop.getId())
            .name(shop.getName())
            .cuisineType(shop.getCuisineType())
            .flavorTags(shop.getFlavorTags())
            .avgPrice(shop.getAvgPrice())
            .address(shop.getAddress())
            .rating(shop.getRating())
            .description(shop.getDescription())
            .build();
    }
    
    private FoodSearchResult.DishInfo toDishInfo(Dish dish) {
        return FoodSearchResult.DishInfo.builder()
            .id(dish.getId())
            .name(dish.getName())
            .shopId(dish.getShop().getId())
            .shopName(dish.getShop().getName())
            .price(dish.getPrice())
            .description(dish.getDescription())
            .flavorTags(dish.getFlavorTags())
            .isSpicy(dish.getIsSpicy())
            .build();
    }
}
