package com.ECom.ecommerce.services.brand;

import java.util.List;

import com.ECom.ecommerce.dtos.brand.request.BrandRequest;
import com.ECom.ecommerce.dtos.brand.response.BrandResponse;


public interface BrandService { 

    //--------------- create ---------------\\
    BrandResponse addBrand(BrandRequest brandRequest);
    
    //--------------- update ---------------\\
    BrandResponse update(Long brandId, BrandRequest brandRequest);
    
    //--------------- delete ---------------\\
    void delete(Long brandId);

    //--------------- fetch ---------------\\
    BrandResponse findById(Long brandId);
    List<BrandResponse> findAll();
    BrandResponse findByName(String name);
    


}
