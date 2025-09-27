package com.ECom.ecommerce.services.cart;

import com.ECom.ecommerce.dtos.cart.request.AddCartItemRequest;
import com.ECom.ecommerce.dtos.cart.request.UpdateCartItemRequest;
import com.ECom.ecommerce.dtos.cart.response.CartResponse;

public interface CartService {
    
    CartResponse getCartByUserId(String userId);
    
    CartResponse addCartItem(String userId, AddCartItemRequest addCartItemRequest);

    CartResponse updateCartItem(String userId, UpdateCartItemRequest updateCartItemRequest);
    
    CartResponse removeCartItem(String userId, String productId);

    CartResponse clearCart(String userId);
    
}
