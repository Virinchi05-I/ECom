package com.ECom.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.product_variant.request.ProductVariantRequest;
import com.ECom.ecommerce.dtos.product_variant.response.ProductVariantResponse;
import com.ECom.ecommerce.services.productvariant.ProductVariantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productVariant")
@CrossOrigin
@RequiredArgsConstructor
public class ProductVariantController {
    
    private final ProductVariantService productVariantService;

    @PostMapping("/createProductVariant")
    public void createProductVariant(@RequestBody ProductVariantRequest productVariantRequest) {
        productVariantService.createProductVariant(productVariantRequest);
    }

    @PatchMapping("/updateProductVariant")
    public void updateProductVariant(@RequestBody ProductVariantRequest productVariantRequest) {
        productVariantService.updateProductVariant(productVariantRequest);
    }

    @DeleteMapping("/deleteProductVariant/{variantId}")
    public void deleteProductVariant(@PathVariable Long variantId) {
        productVariantService.deleteProductVariant(variantId);
    }

    @GetMapping("/searchVariants")
    public List<ProductVariantResponse> searchVariants(Long productId, String color, String size, int stock) {
        return productVariantService.searchVariants(productId, color, size, stock);
    }

    @GetMapping("/findAll")
    public List<ProductVariantResponse> findAll() {
        return productVariantService.findAll();
    }

    @GetMapping("/findById/{variantId}")
    public Optional<ProductVariantResponse> findById(@PathVariable Long variantId) {
        return productVariantService.findById(variantId);
    }
}
