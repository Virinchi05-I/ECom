package com.ECom.ecommerce.entities;

public enum OrderStatus {
    PENDING,      // order placed
    PROCESSING,
    IN_TRANSIT,
    CANCELLED,   // being prepared / packed
    SHIPPED,
    OUT_FOR_DELIVERY,      // handed over to delivery
    DELIVERED     // completed

}
