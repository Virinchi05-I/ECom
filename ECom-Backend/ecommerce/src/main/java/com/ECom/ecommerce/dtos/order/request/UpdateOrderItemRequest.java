package com.ECom.ecommerce.dtos.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderItemRequest {

    private Long productId;
    private String productName;
    private int quantity;
    private  BigDecimal price;
}
