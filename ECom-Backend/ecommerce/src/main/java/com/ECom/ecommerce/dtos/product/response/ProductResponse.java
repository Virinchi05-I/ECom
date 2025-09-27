package com.ECom.ecommerce.dtos.product.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String name;
    private Long brandId;
    private Long categoryId;
    private Long itemType;
    private String specification;
    private String description;
    private BigDecimal price;
    
}