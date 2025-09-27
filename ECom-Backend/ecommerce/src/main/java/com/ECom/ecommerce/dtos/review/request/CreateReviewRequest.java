package com.ECom.ecommerce.dtos.review.request;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {

    private Long reviewId;
    private Long userId;
    private Long productId;
    private String productName;
    private String content;
    private int rating;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
