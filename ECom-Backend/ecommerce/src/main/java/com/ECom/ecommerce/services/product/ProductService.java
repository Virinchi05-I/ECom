package com.ECom.ecommerce.services.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.ECom.ecommerce.dtos.product.request.CreateProductRequest;
import com.ECom.ecommerce.dtos.product.request.UpdateProductRequest;
import com.ECom.ecommerce.dtos.product.response.ProductListResponse;
import com.ECom.ecommerce.dtos.product.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest createProductRequest);

    ProductResponse updateProduct(Long productId, UpdateProductRequest updateProductRequest);

    void deleteProduct(Long productId);

    Optional<ProductResponse> findProductById(Long productId);
    
    List<ProductResponse> findAll();
    
    List<ProductListResponse> findByCategory(Long categoryId);
    List<ProductListResponse> findByBrand(Long brandId);
    List<ProductListResponse> findByItemType(Long itemTypeId);

    List<ProductListResponse> findByName(String name);
    List<ProductListResponse> findByCategoryAndBrand(Long categoryId, Long brandId);
    List<ProductListResponse> findByCategoryAndBrandAndPrice(Long categoryId, Long brandId, BigDecimal price);

    List<ProductListResponse> searchProducts(Long categoryId, Long brandId, BigDecimal price, Long itemTypeId);
    
}
