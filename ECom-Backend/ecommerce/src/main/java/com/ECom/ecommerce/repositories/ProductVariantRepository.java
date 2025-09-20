package com.ECom.ecommerce.repositories;

import com.ECom.ecommerce.entities.ProductVariant;
import com.ECom.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {

    // Find all variants for a given product
    List<ProductVariant> findByProduct(Product product);

    Optional<ProductVariant> findByProductAndColorAndSize(Product product, String color, String size);

    List<ProductVariant> findByProductAndColor(Product product, String color);

    List<ProductVariant> findByProductAndSize(Product product, String size);

    List<ProductVariant> findByProductAndStockGreaterThan(Product product, int stock);

}
