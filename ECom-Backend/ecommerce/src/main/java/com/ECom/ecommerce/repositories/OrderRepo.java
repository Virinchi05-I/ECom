package com.ECom.ecommerce.repositories;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Address;
import com.ECom.ecommerce.entities.Order;
import com.ECom.ecommerce.entities.OrderStatus;
import com.ECom.ecommerce.entities.User;


@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    
    // --------------- customer ---------------- //

    List<Order> findByUser(User user);

    List<Order> findByUserAndOrderstatus(User user, String status);


    // -------- Admin / Reporting -------- //

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Order> findByDeliveryDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Order> findByOrderStatus(String orderStatus);
    
    List<Order> findByOrderStatusAndOrderDateBetween(String orderStatus, LocalDateTime start, LocalDateTime end);
    

    // --------------- Seller ---------------- //

    List<Order> findByDeliveryAddress(Address address);

    List<Order> findByDeliveryAddressAndOrderStatus(Address address, String orderStatus);

    List<Order> findByDeliveryDateAndDeliveryAddress(LocalDateTime date, Address address);

    List<Order> findByOrderstatus(OrderStatus status);

    List<Order> findByUserIdAndOrderstatus(Long userId, OrderStatus status);
    
    
}
