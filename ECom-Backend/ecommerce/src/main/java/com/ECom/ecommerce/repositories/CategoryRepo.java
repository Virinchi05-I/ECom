package com.ECom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByCategoryName(String name);

}
