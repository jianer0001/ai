package com.example.foodai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "shops")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(length = 100)
    private String cuisineType;
    
    @Column(length = 500)
    private String flavorTags;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal avgPrice;
    
    @Column(length = 500)
    private String address;
    
    @Column(precision = 3, scale = 1)
    private BigDecimal rating;
    
    @Column(length = 1000)
    private String description;
}
