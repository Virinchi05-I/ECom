package com.ECom.ecommerce.dtos.brand.request;

public record BrandRequest(
    String name,      // brand name
    String logoUrl    // optional, if seller can upload logo
) {}

