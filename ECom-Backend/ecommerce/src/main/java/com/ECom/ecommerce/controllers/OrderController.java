package com.ECom.ecommerce.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.order.request.AddOrderItemRequest;
import com.ECom.ecommerce.dtos.order.request.CreateOrderRequest;
import com.ECom.ecommerce.dtos.order.request.UpdateOrderItemRequest;
import com.ECom.ecommerce.dtos.order.response.OrderListResponse;
import com.ECom.ecommerce.dtos.order.response.OrderResponse;
import com.ECom.ecommerce.services.order.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {
 
    private final OrderService orderService;
    
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long orderId, 
            @RequestParam String orderStatus,
            @RequestParam String role
            ) {
            orderService.updateOrderStatus(orderId, orderStatus, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderListResponse>> getAllOrders() {
        
        return ResponseEntity.ok(orderService.findAll());
    }
    @GetMapping("/orders/filterByUser")
    public ResponseEntity<List<OrderListResponse>> getByUser(
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(orderService.findByUser(userId));
    }
    @GetMapping("/orders/filterByStatus")
    public ResponseEntity<List<OrderListResponse>> getByStatus(
        @RequestParam String status
    ) {
        return ResponseEntity.ok(orderService.findByStatus(status));
    }
    @GetMapping("/orders/filterByUserAndStatus")
    public ResponseEntity<List<OrderListResponse>> getByUserAndStatus(
        @RequestParam Long userId,
        @RequestParam String status
    ) {
        return ResponseEntity.ok(orderService.findByUserAndStatus(userId, status));
    }

    @PostMapping("/orders/add/{orderId}")
    public ResponseEntity<OrderResponse> add(
        @PathVariable Long orderId,
        @RequestBody AddOrderItemRequest addOrderItemRequest
    ) {
        return ResponseEntity.ok(orderService.addOrderItem(orderId, addOrderItemRequest));
    }

    @PostMapping("/orders/update/{orderId}")
    public ResponseEntity<OrderResponse> update(
        @PathVariable Long orderId,
        @RequestBody UpdateOrderItemRequest updateOrderItemRequest
    ) {
        return ResponseEntity.ok(orderService.updateOrderItem(orderId, updateOrderItemRequest));
    }

    @PostMapping("/orders/remove/{orderId}/{orderItemId}")
    public ResponseEntity<OrderResponse> remove(
        @PathVariable Long orderId,
        @PathVariable Long orderItemId
    ) {
        return ResponseEntity.ok(orderService.removeOrderItem(orderId, orderItemId));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancel(
        @PathVariable Long orderId,
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, userId));
    }
   
}
