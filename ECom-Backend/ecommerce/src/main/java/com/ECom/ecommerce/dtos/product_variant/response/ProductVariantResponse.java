package com.ECom.ecommerce.dtos.product_variant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductVariantResponse {
    
    private Long variantId;
    private Long productId;
    private String color;
    private String size;
    private int stock;
}
