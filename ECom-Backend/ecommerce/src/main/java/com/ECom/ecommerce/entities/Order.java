package com.ECom.ecommerce.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderstatus;
    
    @CreationTimestamp
    private LocalDateTime orderDate;
    
    @UpdateTimestamp
    private LocalDateTime deliveryDate;

    public BigDecimal getTotalAmount() {

        BigDecimal total = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getTotalPrice());
        }
        return total;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderstatus = status;
    }



    
}
