package com.ECom.ecommerce.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {

    Brand findByName(String name);

}
