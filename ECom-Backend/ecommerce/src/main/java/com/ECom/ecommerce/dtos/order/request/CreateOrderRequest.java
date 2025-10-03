package com.ECom.ecommerce.dtos.order.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ECom.ecommerce.entities.OrderStatus;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Long id;
    private User user;
    private Address address;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;


   


}
