package com.ECom.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECom.ecommerce.entities.Brand;

public interface BrandRepo extends JpaRepository<Brand, String> {

    List<Brand> findByName(String name);

}
