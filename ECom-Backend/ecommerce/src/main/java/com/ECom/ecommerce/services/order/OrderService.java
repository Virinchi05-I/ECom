package com.ECom.ecommerce.services.order;

import java.util.List;
import java.util.Optional;

import com.ECom.ecommerce.dtos.order.request.AddOrderItemRequest;
import com.ECom.ecommerce.dtos.order.request.CreateOrderRequest;
import com.ECom.ecommerce.dtos.order.request.UpdateOrderItemRequest;
import com.ECom.ecommerce.dtos.order.response.OrderListResponse;
import com.ECom.ecommerce.dtos.order.response.OrderResponse;
import com.ECom.ecommerce.entities.OrderStatus;

public interface OrderService {

    // Order management
    OrderResponse createOrder(CreateOrderRequest createOrderRequest);
    Optional<OrderResponse> findOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String status, String ROLE);
    boolean deleteOrder(Long orderId);

    // Query orders
    List<OrderListResponse> findAll();
    List<OrderListResponse> findByUser(Long userId);
    List<OrderListResponse> findByStatus(String status);
    List<OrderListResponse> findByUserAndStatus(Long userId, String status);

    // Manage order items within an order
    OrderResponse addOrderItem(Long orderId, AddOrderItemRequest request);
    OrderResponse updateOrderItem(Long orderId, UpdateOrderItemRequest request);
    OrderResponse removeOrderItem(Long orderId, Long orderItemId);
    OrderResponse cancelOrder(Long orderId, Long userId);
    OrderResponse completeOrder(Long orderId, Long userId);

    // Alternative user-specific
    List<OrderResponse> getOrdersByUserId(Long userId);
    OrderResponse getOrderById(Long orderId);
}
