package com.ECom.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ECom.ecommerce.entities.Brand; 
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.ItemType;
import com.ECom.ecommerce.entities.Product;


public interface ProductRepo extends JpaRepository<Product, String> {
    
    List<Product> findByName(String name);

    List<Product> findByBrand(Brand brand);
    List<Product> findByBrandAndName(Brand brand, String name);
    
    List<Product> findByItemType(ItemType itemType);
    List<Product> findByItemTypeAndName(ItemType itemType, String name);
    
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryAndName(Category category, String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByBrandAndCategory(Brand brand, Category category);
   
    List<Product> findByBrandAndItemType(Brand brand, ItemType itemType);
    
    List<Product> findByCategoryAndItemType(Category category, ItemType itemType);

    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.category = :category AND p.itemType = :itemType")
    List<Product> findByBrandAndCategoryAndItemType(@Param("brand") Brand brand, @Param("category") Category category, @Param("itemType") ItemType itemType);
    
    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.category = :category AND p.price = :price")
    List<Product> findByBrandAndCategoryAndPrice(@Param("brand") Brand brand, @Param("category") Category category, @Param("price") Product price);
    
    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.itemType = :itemType AND p.price = :price")
    List<Product> findByBrandAndItemTypeAndPrice(@Param("brand") Brand brand, @Param("itemType") ItemType itemType, @Param("price") double price);
   
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.itemType = :itemType AND p.price = :price")
    List<Product> findByCategoryAndItemTypeAndPrice(@Param("category") Category category, @Param("itemType") ItemType itemType, @Param("price") double price);

}
