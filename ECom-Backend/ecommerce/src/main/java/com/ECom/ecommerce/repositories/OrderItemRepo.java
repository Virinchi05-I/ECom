package com.ECom.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Order;
import com.ECom.ecommerce.entities.OrderItem;
import com.ECom.ecommerce.entities.Product;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order); 

    List<OrderItem> findByProduct(Product product);

    Optional<OrderItem> findByOrderAndProduct(Order order, Product product);
}
