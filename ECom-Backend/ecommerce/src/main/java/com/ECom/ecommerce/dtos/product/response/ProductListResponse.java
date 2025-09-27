package com.ECom.ecommerce.dtos.product.response;

import java.util.List;

import com.ECom.ecommerce.entities.CartItem;
import com.ECom.ecommerce.entities.OrderItem;
import com.ECom.ecommerce.entities.ProductImage;
import com.ECom.ecommerce.entities.ProductVariant;
import com.ECom.ecommerce.entities.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private List<ProductResponse> products;
    private List<ProductImage> images;
    private List<CartItem> cartItems;
    private List<OrderItem> orderItems;
    private List<Review> reviews;
    private List<ProductVariant> productVariants;
}
