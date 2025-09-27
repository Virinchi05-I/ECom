package com.ECom.ecommerce.services.review;

import java.util.List;
import java.util.Optional;

import com.ECom.ecommerce.dtos.review.request.CreateReviewRequest;
import com.ECom.ecommerce.dtos.review.request.UpdateReviewRequest;
import com.ECom.ecommerce.dtos.review.response.ReviewResponse;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest createReviewRequest);

    ReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest);

    void deleteReview(Long reviewId);
    
    List<ReviewResponse> getReviewsByProductId(Long productId);

    List<ReviewResponse> getReviewsByUserId(Long userId);

    List<ReviewResponse> findAll();
    
    Optional<ReviewResponse> getReviewById(Long reviewId);

    List<ReviewResponse> searchReviews(Long productId, Long userId, Integer rating);

}
