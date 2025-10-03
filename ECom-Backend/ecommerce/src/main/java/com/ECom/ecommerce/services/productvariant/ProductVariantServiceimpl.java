package com.ECom.ecommerce.services.productvariant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.product_variant.request.ProductVariantRequest;
import com.ECom.ecommerce.dtos.product_variant.response.ProductVariantResponse;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.entities.ProductVariant;
import com.ECom.ecommerce.repositories.ProductRepo;
import com.ECom.ecommerce.repositories.ProductVariantRepo;

@Service
public class ProductVariantServiceimpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public ProductVariantResponse createProductVariant(ProductVariantRequest productVariantRequest) {
        
        ProductVariant productVariant = new ProductVariant();

        productVariant.setVariantId(productVariantRequest.getVariantId());


        Product product = new Product();
        product.setProductId(productVariantRequest.getProductId());
        productVariant.setProduct(product);
        
        productVariant.setColor(productVariantRequest.getColor());
        productVariant.setSize(productVariantRequest.getSize());
        productVariant.setStock(productVariantRequest.getStock());
        
        ProductVariant savedProductVariant = productVariantRepo.save(productVariant);

        return mapProductVariantToResponse(savedProductVariant);
    }


    @Override
    public ProductVariantResponse updateProductVariant(ProductVariantRequest productVariantRequest) {
        
        ProductVariant productVariant = productVariantRepo.findById(productVariantRequest.getVariantId())
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        Product product = new Product();
        product.setProductId(productVariantRequest.getProductId());
        productVariant.setProduct(product);
        
        productVariant.setColor(productVariantRequest.getColor());
        productVariant.setSize(productVariantRequest.getSize());
        productVariant.setStock(productVariantRequest.getStock());
        
        ProductVariant savedProductVariant = productVariantRepo.save(productVariant);

        return mapProductVariantToResponse(savedProductVariant);
    }

    @Override
    public void deleteProductVariant(Long variantId) {
        
        ProductVariant productVariant = productVariantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        productVariantRepo.delete(productVariant);
    }

    @Override
    public List<ProductVariantResponse> findAll() {
        
        List<ProductVariant> productVariants = productVariantRepo.findAll();

        return productVariants.stream().map(this::mapProductVariantToResponse).toList();
    }

    @Override
    public Optional<ProductVariantResponse> findById(Long variantId) {
        
         ProductVariant productVariant = productVariantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Product variant not found"));
        
        return Optional.of(mapProductVariantToResponse(productVariant));
    }

    @Override
    public List<ProductVariantResponse> searchVariants(Long productId, String color, String size, int stock) {
      Product product = productRepo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    List<ProductVariant> productVariants;

    if (color != null && size != null && stock != 0) {
        productVariants = productVariantRepo.findByProductAndColorAndSizeAndStock(product, color, size, stock);
    } else if (color != null && size != null) {
        productVariants = productVariantRepo.findByProductAndColorAndSize(product, color, size);
    } else if (color != null) {
        productVariants = productVariantRepo.findByProductAndColor(product, color);
    } else if (size != null) {
        productVariants = productVariantRepo.findByProductAndSize(product, size);
    } else if (stock != 0) {
        productVariants = productVariantRepo.findByProductAndStockGreaterThan(product, 0);
    } else {
        productVariants = productVariantRepo.findByProduct(product);
    }

    return productVariants.stream()
            .map(this::mapProductVariantToResponse)
            .collect(Collectors.toList());
    }
    
    private ProductVariantResponse mapProductVariantToResponse(ProductVariant productVariant){
        
        ProductVariantResponse response = new ProductVariantResponse();

        response.setVariantId(productVariant.getVariantId());
        response.setProductId(productVariant.getProduct().getProductId() != null ? productVariant.getProduct().getProductId() : null);
        response.setColor(productVariant.getColor());
        response.setSize(productVariant.getSize());
        response.setStock(productVariant.getStock());
        
        return response;
    }
}
