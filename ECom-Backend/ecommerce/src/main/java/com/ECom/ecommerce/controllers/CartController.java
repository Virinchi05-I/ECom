package com.ECom.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.cart.request.AddCartItemRequest;
import com.ECom.ecommerce.dtos.cart.request.UpdateCartItemRequest;
import com.ECom.ecommerce.dtos.cart.response.CartResponse;
import com.ECom.ecommerce.services.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carts")
@CrossOrigin
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @RequestMapping("/{UserId}/{CartId}")
    public ResponseEntity<CartResponse> filterByUserId(
        @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartResponse> add(
        @RequestParam("userId") Long userId,
        @RequestBody AddCartItemRequest addCartItemRequest) {
        return ResponseEntity.ok(cartService.addCartItem(userId, addCartItemRequest));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<CartResponse> update(
        @RequestParam("userId") Long userId,
        @RequestBody UpdateCartItemRequest updateCartItemRequest) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, updateCartItemRequest));
    }   

    @PostMapping("/remove/{userId}")
    public ResponseEntity<CartResponse> remove(
        @RequestParam("userId") Long userId,
        @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(cartService.removeCartItem(userId, productId));
    }

    @PostMapping("/clear/{userId}")
    public ResponseEntity<CartResponse> clear(
        @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
} 
