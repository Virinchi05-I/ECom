package com.ECom.ecommerce.dtos.product_variant.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantRequest{
    
    private Long variantId;
    private Long productId;
    private String color;
    private String size;
    private int stock;
}
