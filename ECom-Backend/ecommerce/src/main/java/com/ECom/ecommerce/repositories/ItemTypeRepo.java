package com.ECom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.ECom.ecommerce.entities.ItemType;

public interface ItemTypeRepo extends JpaRepository<ItemType, String> {

    List<ItemType> findByName(String name);
}
