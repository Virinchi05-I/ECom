package com.ECom.ecommerce.services.productvariant;

import java.util.List;
import java.util.Optional;

import com.ECom.ecommerce.dtos.product_variant.request.ProductVariantRequest;
import com.ECom.ecommerce.dtos.product_variant.response.ProductVariantResponse;

public interface ProductVariantService {
    
    ProductVariantResponse createProductVariant(ProductVariantRequest productVariantRequest);

    ProductVariantResponse updateProductVariant(ProductVariantRequest productVariantRequest);

    void deleteProductVariant(Long variantId);

    List<ProductVariantResponse> findAll();

    Optional<ProductVariantResponse> findById(Long variantId);

    List<ProductVariantResponse> searchVariants(Long productId, String color, String size, Boolean inStock);
}
