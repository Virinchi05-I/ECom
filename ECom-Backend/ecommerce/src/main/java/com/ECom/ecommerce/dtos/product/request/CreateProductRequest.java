package com.ECom.ecommerce.dtos.product.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String name;
    private Long brandId;
    private Long categoryId;
    private Long itemTypeId;
    private String specification;
    private String description;
    private BigDecimal price;
}
