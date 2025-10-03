package com.ECom.ecommerce.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.review.request.CreateReviewRequest;
import com.ECom.ecommerce.dtos.review.request.UpdateReviewRequest;
import com.ECom.ecommerce.dtos.review.response.ReviewResponse;
import com.ECom.ecommerce.services.review.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/createReview")
    public ResponseEntity<ReviewResponse> add(
        @RequestBody CreateReviewRequest createReviewRequest) {
        return ResponseEntity.ok(reviewService.createReview(createReviewRequest));
    }
    
    @PatchMapping("/updateReview")
    public ResponseEntity<ReviewResponse> update(
        @RequestParam Long reviewId, 
        @RequestBody UpdateReviewRequest updateReviewRequest) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, updateReviewRequest));
    }

    @DeleteMapping("/deleteReview")
    public ResponseEntity<Void> delete(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filterReviewsByProductId")
    public ResponseEntity<List<ReviewResponse>> getByProductId(@RequestParam Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/filterReviewsByUserId")
    public ResponseEntity<List<ReviewResponse>> getByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/filterAll")
    public ResponseEntity<List<ReviewResponse>> getAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @GetMapping("/filterReviewById")
    public ResponseEntity<ReviewResponse> filterReviewById(@RequestParam Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId).get());
    }

    @GetMapping("/searchReviews")
    public ResponseEntity<List<ReviewResponse>> searchReviews(@RequestParam Long productId, @RequestParam Long userId, @RequestParam Integer rating) {
        return ResponseEntity.ok(reviewService.searchReviews(productId, userId, rating));
    }
}
