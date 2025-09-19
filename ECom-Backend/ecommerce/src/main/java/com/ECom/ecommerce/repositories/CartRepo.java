package com.ECom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECom.ecommerce.entities.Cart;
import com.ECom.ecommerce.entities.User;

public interface CartRepo extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(String userId);

    void deleteByUser(User user);

}
