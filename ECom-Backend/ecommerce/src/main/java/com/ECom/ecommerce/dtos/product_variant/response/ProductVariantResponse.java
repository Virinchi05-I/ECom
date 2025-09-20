package com.ECom.ecommerce.dtos.product_variant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductVariantResponse {
    
    private String variantId;
    private String productId;
    private String color;
    private String size;
    private int stock;
}
