package com.ECom.ecommerce.dtos.order.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
