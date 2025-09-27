package com.ECom.ecommerce.services.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.category.request.CategoryRequest;
import com.ECom.ecommerce.dtos.category.response.CategoryResponse;
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        
        Category category = new Category();
        category.setCategoryId(categoryRequest.getCategoryId());
        category.setCategoryName(categoryRequest.getCategoryName());
        
        CategoryResponse response = mapToCategoryResponse(category);

        categoryRepo.save(category);
        
        return response;
    }

    @Override
    public CategoryResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepo.findById(categoryId).get();


        if(categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        
        CategoryResponse response = mapToCategoryResponse(category);

        categoryRepo.save(category);
        
        return response;
    }

    @Override
    public void delete(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).get();

        categoryRepo.delete(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(this::mapToCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> findByName(String name) {
        List<Category> categories = categoryRepo.findByName(name);
        return categories.stream().map(this::mapToCategoryResponse).toList();
    }
    
    private CategoryResponse mapToCategoryResponse(Category category) {
        
        CategoryResponse response =  new CategoryResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        
        
        return response;

    }
}
