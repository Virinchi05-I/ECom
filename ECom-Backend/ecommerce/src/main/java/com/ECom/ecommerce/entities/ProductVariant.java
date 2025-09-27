package com.ECom.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long variantId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String color;
    private String size;
    private int stock;

    @Transient
    private boolean isInStock(){
        return stock > 0;
    }
}

