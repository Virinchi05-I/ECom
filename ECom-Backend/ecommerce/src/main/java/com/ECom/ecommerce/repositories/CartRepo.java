package com.ECom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Cart;
import com.ECom.ecommerce.entities.User;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(String userId);

    void deleteByUser(User user);

}
