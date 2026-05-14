package com.example.foodai.repository;

import com.example.foodai.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    
    List<Dish> findByShopId(Long shopId);
    
    @Query("SELECT d FROM Dish d WHERE " +
           "(:shopId IS NULL OR d.shop.id = :shopId) AND " +
           "(:maxPrice IS NULL OR d.price <= :maxPrice) AND " +
           "(:isSpicy IS NULL OR d.isSpicy = :isSpicy)")
    List<Dish> findDishesByCriteria(
        @Param("shopId") Long shopId,
        @Param("maxPrice") Double maxPrice,
        @Param("isSpicy") Boolean isSpicy
    );
    
    @Query("SELECT d FROM Dish d JOIN d.shop s WHERE " +
           "(:cuisineType IS NULL OR s.cuisineType = :cuisineType) AND " +
           "(:maxPrice IS NULL OR d.price <= :maxPrice) AND " +
           "(:isSpicy IS NULL OR d.isSpicy = :isSpicy)")
    List<Dish> findDishesWithShopCriteria(
        @Param("cuisineType") String cuisineType,
        @Param("maxPrice") Double maxPrice,
        @Param("isSpicy") Boolean isSpicy
    );
    
    @Query("SELECT d FROM Dish d JOIN d.shop s WHERE " +
           "(:cuisineType IS NULL OR s.cuisineType = :cuisineType) AND " +
           "(:maxPrice IS NULL OR d.price <= :maxPrice) AND " +
           "(:isSpicy IS NULL OR d.isSpicy = :isSpicy) AND " +
           "(:flavorPreference IS NULL OR s.flavorTags LIKE %:flavorPreference% OR d.flavorTags LIKE %:flavorPreference%)")
    List<Dish> findDishesWithFlavorPreference(
        @Param("cuisineType") String cuisineType,
        @Param("maxPrice") Double maxPrice,
        @Param("isSpicy") Boolean isSpicy,
        @Param("flavorPreference") String flavorPreference
    );
}
