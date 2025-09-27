package com.ECom.ecommerce.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Brand; 
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.ItemType;
import com.ECom.ecommerce.entities.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    
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

    List<Product> findByCategoryAndPrice(Category category, BigDecimal price);


    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.category = :category AND p.itemType = :itemType")
    List<Product> findByBrandAndCategoryAndItemType(@Param("brand") Brand brand, @Param("category") Category category, @Param("itemType") ItemType itemType);
    
   
    
    
   
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.brand = :brand AND p.price = :price")
    List<Product> findByCategoryAndBrandAndPrice(@Param("category") Category category, @Param("brand") Brand brand, @Param("price") BigDecimal price);

    
   

}
