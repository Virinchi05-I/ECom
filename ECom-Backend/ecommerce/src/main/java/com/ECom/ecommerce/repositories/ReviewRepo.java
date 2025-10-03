package com.ECom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    // Find all reviews by a user
    List<Review> findByUserId(Long userId);

    // Find all reviews by a product
    List<Review> findByProductProductId(Long productId);

    // Find all reviews by a user and a product
    List<Review> findByProductProductIdAndUserIdAndRating(Long productId, Long userId, int rating);



}
