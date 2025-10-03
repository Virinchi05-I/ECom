package com.ECom.ecommerce.services.review;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.review.request.CreateReviewRequest;
import com.ECom.ecommerce.dtos.review.request.UpdateReviewRequest;
import com.ECom.ecommerce.dtos.review.response.ReviewResponse;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.entities.Review;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.ProductRepo;
import com.ECom.ecommerce.repositories.ReviewRepo;
import com.ECom.ecommerce.repositories.UserRepo;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;


    @Override
    public ReviewResponse createReview(CreateReviewRequest createReviewRequest) {
       
        Review review = new Review();

        User user = userRepo.findById(createReviewRequest.getUserId())
                        .orElseThrow( () -> new IllegalArgumentException("User not found"));

        Product product = productRepo.findById(createReviewRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("P roduct not found"));

        review.setReviewId(createReviewRequest.getReviewId());
        review.setUser(user);
        review.setProduct(product);
        review.setProductName(createReviewRequest.getProductName());
        review.setContent(createReviewRequest.getContent());
        review.setRating(createReviewRequest.getRating());
        review.setCreateDate(createReviewRequest.getCreateDate());
        review.setUpdateDate(createReviewRequest.getUpdateDate());

        Review savedReview = reviewRepo.save(review);
        
        return mapReviewToResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest) {
        

        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (updateReviewRequest.getUserId() != null) {
        User user = userRepo.findById(updateReviewRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        review.setUser(user);
    }

    if (updateReviewRequest.getProductId() != null) {
        Product product = productRepo.findById(updateReviewRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        review.setProduct(product);
    }

    if (updateReviewRequest.getProductName() != null) {
        review.setProductName(updateReviewRequest.getProductName());
    }

    if (updateReviewRequest.getContent() != null) {
        review.setContent(updateReviewRequest.getContent());
    }

    if (updateReviewRequest.getRating() != 0) {
        review.setRating(updateReviewRequest.getRating());
    }

    if (updateReviewRequest.getCreateDate() != null) {
        review.setCreateDate(updateReviewRequest.getCreateDate());
    }

    if (updateReviewRequest.getUpdateDate() != null) {
        review.setUpdateDate(updateReviewRequest.getUpdateDate());
    }


        Review savedReview = reviewRepo.save(review);
        
        return mapReviewToResponse(savedReview);

    }

    @Override
    public void deleteReview(Long reviewId) {
        

        Review review = reviewRepo.findById(reviewId)
                .orElseThrow();

        reviewRepo.delete(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        
        Product product = productRepo.findById(productId)
                    .orElseThrow( () -> new IllegalArgumentException("Product not found"));

        List<Review> reviews = reviewRepo.findByProductProductId(product.getProductId());
        
        return reviews.stream().map(this::mapReviewToResponse).toList();
    }
 
    @Override
    public List<ReviewResponse> getReviewsByUserId(Long userId) {
       
        User user = userRepo.findById(userId)
                    .orElseThrow();

        List<Review> reviews = reviewRepo.findByUserId(user.getId());
        
        return reviews.stream().map(this::mapReviewToResponse).toList();
    }

    @Override
    public List<ReviewResponse> findAll() {
       
        List<Review> reviews = reviewRepo.findAll();
        
        return reviews.stream().map(this::mapReviewToResponse).toList();
    }

    @Override
    public Optional<ReviewResponse> getReviewById(Long reviewId) {
        
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return Optional.of(mapReviewToResponse(review));
    }

    @Override
    public List<ReviewResponse> searchReviews(Long productId, Long userId, Integer rating) {
        
        List<Review> reviews = reviewRepo.findByProductProductIdAndUserIdAndRating(productId, userId, rating);
        
        return reviews.stream().map(this::mapReviewToResponse).toList();
    }
    
    private ReviewResponse mapReviewToResponse(Review review) {
       
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getReviewId());
        response.setUserId(review.getUser().getId());
        response.setProductId(review.getProduct().getProductId());
        response.setProductName(review.getProductName());
        response.setContent(review.getContent());
        response.setRating(review.getRating());
        response.setCreateDate(review.getCreateDate());
        response.setUpdateDate(review.getUpdateDate());
        
        return response;
    }
}
