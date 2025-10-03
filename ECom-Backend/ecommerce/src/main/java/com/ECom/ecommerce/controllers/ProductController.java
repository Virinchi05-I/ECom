package com.ECom.ecommerce.controllers;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.product.request.CreateProductRequest;
import com.ECom.ecommerce.dtos.product.request.UpdateProductRequest;
import com.ECom.ecommerce.dtos.product.response.ProductListResponse;
import com.ECom.ecommerce.dtos.product.response.ProductResponse;    
import com.ECom.ecommerce.services.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/ECom/user/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> add(@RequestBody CreateProductRequest createProductRequest){
        return ResponseEntity.ok(productService.createProduct(createProductRequest));
    }
    
    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable("id") Long productId, 
            @RequestBody UpdateProductRequest updateProductRequest){
        return ResponseEntity.ok(productService.updateProduct(productId, updateProductRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filterById/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable("id") Long productId){
        
        Optional<ProductResponse> productOpt = productService.findProductById(productId);
        
        return productOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/filterAll")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        return ResponseEntity.ok(productService.findAll());     
    }

    @GetMapping("/FilterByCategory")
    public ResponseEntity<List<ProductListResponse>> getByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @GetMapping("/filterByBrand")
    public ResponseEntity<List<ProductListResponse>> getByBrand(@RequestParam Long brandId) {
        return ResponseEntity.ok(productService.findByBrand(brandId));
    }

    @GetMapping("/filterByItemType")
    public ResponseEntity<List<ProductListResponse>> getByItemType(@RequestParam Long itemTypeId) {
        return ResponseEntity.ok(productService.findByItemType(itemTypeId));
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<ProductListResponse>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/filterByCategoryAndBrand")
    public ResponseEntity<List<ProductListResponse>> getByCategoryAndBrand(
            @RequestParam Long categoryId,
            @RequestParam Long brandId) {
        return ResponseEntity.ok(productService.findByCategoryAndBrand(categoryId, brandId));
    }

    @GetMapping("/filterByCategoryAndBrandAndPrice")
    public ResponseEntity<List<ProductListResponse>> getByCategoryAndBrandAndPrice(
            @RequestParam Long categoryId,
            @RequestParam Long brandId,
            @RequestParam BigDecimal price) {
        return ResponseEntity.ok(productService.findByCategoryAndBrandAndPrice(categoryId, brandId, price));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductListResponse>> search(
            @RequestParam Long categoryId,
            @RequestParam Long brandId,
            @RequestParam BigDecimal price,
            @RequestParam Long itemTypeId) {
        return ResponseEntity.ok(productService.searchProducts(categoryId, brandId, price, itemTypeId));
    } 
}
