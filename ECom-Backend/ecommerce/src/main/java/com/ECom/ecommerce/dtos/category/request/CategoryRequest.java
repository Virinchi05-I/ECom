package com.ECom.ecommerce.dtos.category.request;

public record CategoryRequest(
    String name,       // category name
    String parentId    // optional, if supporting sub-categories
) {}

