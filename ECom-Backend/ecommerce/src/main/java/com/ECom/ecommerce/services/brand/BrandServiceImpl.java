package com.ECom.ecommerce.services.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.brand.request.BrandRequest;
import com.ECom.ecommerce.dtos.brand.response.BrandResponse;
import com.ECom.ecommerce.entities.Brand;
import com.ECom.ecommerce.repositories.BrandRepo;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepo brandRepo;
    @Override
    public BrandResponse addBrand(BrandRequest brandRequest) {
        
        Brand brand = new Brand();
        brand.setBrandId(brandRequest.getBrandId());
        brand.setBrandName(brandRequest.getBrandName());
        brand.setLogoUrl(brandRequest.getLogoUrl());

        Brand savedBrand = brandRepo.save(brand);

        return mapToBrandResponse(savedBrand);
    }

    @Override
    public BrandResponse update(Long brandId, BrandRequest brandRequest) {
        Brand brand = brandRepo.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
       
       if(brandRequest.getBrandName() != null) brand.setBrandName(brandRequest.getBrandName());
       if(brandRequest.getLogoUrl() != null) brand.setLogoUrl(brandRequest.getLogoUrl()); 
     
        Brand savedBrand = brandRepo.save(brand);

        return mapToBrandResponse(savedBrand);
    }

    @Override
    public void delete(Long brandId) {
        Brand brand = brandRepo.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brandRepo.delete(brand);
    }

    @Override
    public BrandResponse findById(Long brandId) {
        Brand brand = brandRepo.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        return mapToBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> findAll() {
        List<Brand> brands = brandRepo.findAll();
        return brands.stream().map(this::mapToBrandResponse).toList();
    }

    @Override
    public BrandResponse findByName(String name) {
        Brand brand = brandRepo.findByName(name);
        return mapToBrandResponse(brand);
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        
        BrandResponse response =  new BrandResponse();
        response.setBrandId(brand.getBrandId());
        response.setName(brand.getBrandName());
        response.setLogoUrl(brand.getLogoUrl());
        
        return response;

    }
    
}
