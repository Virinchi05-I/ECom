package com.ECom.ecommerce.dtos.order.request;

import java.math.BigDecimal;

import com.ECom.ecommerce.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderItemRequest {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    
}
