package com.ECom.ecommerce.dtos.category.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest{
    Long categoryId;    // optional, if supporting sub-categories
    String categoryName;       // category name
}

