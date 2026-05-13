package com.example.foodai.repository;

import com.example.foodai.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    @Query("SELECT s FROM Shop s WHERE " +
           "(:cuisineType IS NULL OR s.cuisineType = :cuisineType) AND " +
           "(:maxPrice IS NULL OR s.avgPrice <= :maxPrice) AND " +
           "(:minRating IS NULL OR s.rating >= :minRating)")
    List<Shop> findShopsByCriteria(
        @Param("cuisineType") String cuisineType,
        @Param("maxPrice") Double maxPrice,
        @Param("minRating") Double minRating
    );
}
