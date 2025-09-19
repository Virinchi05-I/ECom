package com.ECom.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECom.ecommerce.entities.Cart;
import com.ECom.ecommerce.entities.CartItem;
import com.ECom.ecommerce.entities.Product;

public interface CartItemRepo extends JpaRepository<CartItem, String> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteByCartAndProduct(Cart cart, Product product);

    void deleteByCart(Cart cart);


}
