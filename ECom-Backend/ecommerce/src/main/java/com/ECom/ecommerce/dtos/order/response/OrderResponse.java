package com.ECom.ecommerce.dtos.order.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ECom.ecommerce.entities.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
 
   
    private Long id;
    private Long userId;
    private Long addressId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    private List<OrderItemResponse> orderItems;
}
