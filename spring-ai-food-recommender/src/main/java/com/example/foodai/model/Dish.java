package com.example.foodai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(length = 1000)
    private String description;
    
    @Column(length = 200)
    private String flavorTags;
    
    @Column(nullable = false)
    private Boolean isSpicy = false;
}
