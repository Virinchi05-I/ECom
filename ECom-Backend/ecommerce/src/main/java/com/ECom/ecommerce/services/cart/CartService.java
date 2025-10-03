package com.ECom.ecommerce.services.cart;

import com.ECom.ecommerce.dtos.cart.request.AddCartItemRequest;
import com.ECom.ecommerce.dtos.cart.request.UpdateCartItemRequest;
import com.ECom.ecommerce.dtos.cart.response.CartResponse;

public interface CartService {
    
    CartResponse getCartByUserId(Long userId);
    
    CartResponse addCartItem(Long userId, AddCartItemRequest addCartItemRequest);

    CartResponse updateCartItem(Long userId, UpdateCartItemRequest updateCartItemRequest);
    
    CartResponse removeCartItem(Long userId, Long productId);

    CartResponse clearCart(Long userId);
    
}
