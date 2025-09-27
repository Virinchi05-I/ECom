package com.ECom.ecommerce.dtos.cart.request;

import java.math.BigDecimal;

import com.ECom.ecommerce.dtos.product.response.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

    private Long productId;
    private int quantity;
    private BigDecimal totalPrice;

    private ProductResponse product;

}
