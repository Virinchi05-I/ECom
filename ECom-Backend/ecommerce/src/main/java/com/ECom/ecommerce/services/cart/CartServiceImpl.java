package com.ECom.ecommerce.services.cart;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.cart.request.AddCartItemRequest;
import com.ECom.ecommerce.dtos.cart.request.UpdateCartItemRequest;
import com.ECom.ecommerce.dtos.cart.response.CartItemResponse;
import com.ECom.ecommerce.dtos.cart.response.CartResponse;
import com.ECom.ecommerce.entities.Cart;
import com.ECom.ecommerce.entities.CartItem;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.repositories.CartRepo;
import com.ECom.ecommerce.repositories.ProductRepo;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;
    
    @Override
    public CartResponse getCartByUserId(String userId) {
        
         Cart cart = cartRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        return mapCartToResponse(cart);
    }

    @Override
    public CartResponse addCartItem(String userId, AddCartItemRequest addCartItemRequest) { 
        
        Cart cart = cartRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        CartItem cartItem = new CartItem();
        
        Product product = productRepo.findById(addCartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
                
        cartItem.setProduct(product);
        cartItem.setQuantity(addCartItemRequest.getQuantity());
        cartItem.getTotalPrice();

        cart.getItems().add(cartItem);
        cartRepo.save(cart);

        return mapCartToResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(String userId, UpdateCartItemRequest updateCartItemRequest) {
        
        Cart cart = cartRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(updateCartItemRequest.getProductId()))
                .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        Product product = productRepo.findById(updateCartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
                
        cartItem.setProduct(product);
        cartItem.setQuantity(updateCartItemRequest.getQuantity());
        cartItem.getTotalPrice();

        if (updateCartItemRequest.getQuantity() <= 0) {
            cart.getItems().remove(cartItem);
        }
        
        Cart updatedCart = cartRepo.save(cart);
        
        return mapCartToResponse(updatedCart);
    }

    @Override
    public CartResponse removeCartItem(String userId, String productId) {
        
         Cart cart = cartRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        cartRepo.save(cart);

        return mapCartToResponse(cart);
    }

    @Override
    public CartResponse clearCart(String userId) {
       
         Cart cart = cartRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        cart.getItems().clear();
        cartRepo.save(cart);

        return mapCartToResponse(cart);
    }

    public CartItemResponse mapCartItemToResponse(CartItem cartItem) {
     
        CartItemResponse cartResponse = new CartItemResponse();
        
        cartResponse.setCartItemId(cartItem.getCartItemId());
        cartResponse.setProductId(cartItem.getProduct().getProductId());
        cartResponse.setProductPrice(cartItem.getProduct().getPrice());
        cartResponse.setProductName(cartItem.getProduct().getName());
        cartResponse.setQuantity(cartItem.getQuantity());
        cartResponse.setTotalPrice(cartItem.getTotalPrice());

        
        return cartResponse;
}

    public CartResponse mapCartToResponse(Cart cart) {
    List<CartItemResponse> itemResponses = cart.getItems().stream()
        .map(this::mapCartItemToResponse)
        .toList();

    BigDecimal totalCartPrice = itemResponses.stream()
        .map(CartItemResponse::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    CartResponse cartResponse = new CartResponse();
    
    cartResponse.setUserId(cart.getUser().getId());
    cartResponse.setItems(itemResponses);
    cartResponse.setTotalCartPrice(totalCartPrice);
    
    return cartResponse;
}

    
}
