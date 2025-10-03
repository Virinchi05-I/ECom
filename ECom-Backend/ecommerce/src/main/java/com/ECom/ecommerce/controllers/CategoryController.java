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

import com.ECom.ecommerce.dtos.category.request.CategoryRequest;
import com.ECom.ecommerce.dtos.category.response.CategoryResponse;
import com.ECom.ecommerce.services.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addcategory")
    public CategoryResponse add(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PatchMapping("/updatecategory")
    public CategoryResponse update(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(categoryRequest.getCategoryId(), categoryRequest);
    }

    @DeleteMapping("/deletecategory")
    public void deletecategory(Long categoryId) {
        categoryService.delete(categoryId);
    }

    @GetMapping("/all")
    public List<CategoryResponse> Allcategories() {
        return categoryService.findAll();
    }

}
