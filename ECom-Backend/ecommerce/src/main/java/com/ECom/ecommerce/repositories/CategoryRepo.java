package com.ECom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECom.ecommerce.entities.Category;


public interface CategoryRepo extends JpaRepository<Category, String> {

    List<Category> findByName(String name);

}
