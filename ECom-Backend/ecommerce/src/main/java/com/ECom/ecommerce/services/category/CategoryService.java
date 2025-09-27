package com.ECom.ecommerce.services.category;

import java.util.List;

import com.ECom.ecommerce.dtos.category.request.CategoryRequest;
import com.ECom.ecommerce.dtos.category.response.CategoryResponse;

public interface CategoryService {
    
    CategoryResponse addCategory(CategoryRequest categoryRequest);
    
    //--------------- update ---------------\\
    CategoryResponse update(Long categoryId, CategoryRequest brandRequest);
    
    //--------------- delete ---------------\\
    void delete(Long categoryId);

    //--------------- fetch ---------------\\
    List<CategoryResponse> findAll();
    List<CategoryResponse> findByName(String name);
    


}
