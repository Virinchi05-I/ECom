package com.ECom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Brand;
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.ItemType;
import com.ECom.ecommerce.entities.Product;

@Repository
public interface ItemTypeRepo extends JpaRepository<ItemType, Long> {

    List<ItemType> findByName(String name);

    Optional<ItemType> findByProduct(Product product);

    List<ItemType> findByCategory(Category category);
    
    List<ItemType> findByBrand(Brand brand);
}
