package com.ECom.ecommerce.dtos.product_variant.request;

public record ProductVariantRequest(
    String variantId,
    String productId,
    String color,
    String size,
    int stock
) {}
