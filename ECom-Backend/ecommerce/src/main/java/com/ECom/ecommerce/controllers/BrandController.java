package com.ECom.ecommerce.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.brand.request.BrandRequest;
import com.ECom.ecommerce.dtos.brand.response.BrandResponse;
import com.ECom.ecommerce.services.brand.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@CrossOrigin
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/addBrand")
    public BrandResponse addBrand(@RequestBody BrandRequest brandRequest) {
        return brandService.addBrand(brandRequest);
    }

    @PatchMapping("/updateBrand")
    public BrandResponse updateBrand(@RequestBody BrandRequest brandRequest) {
        return brandService.update(brandRequest.getBrandId(), brandRequest);
    }

    @DeleteMapping("/deleteBrand")
    public void deleteBrand(Long brandId) {
        brandService.delete(brandId);
    }

    @GetMapping("/getBrandById")
    public BrandResponse getBrandById(Long brandId) {
        return brandService.findById(brandId);
    }

    @GetMapping("/getAllBrands")
    public List<BrandResponse> getBrands() {
        return brandService.findAll();
    }

    @GetMapping("/getBrandByName")
    public BrandResponse getBrandByName(String name) {
        return brandService.findByName(name);
    }
}
