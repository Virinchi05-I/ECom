package com.ECom.ecommerce.services.itemtype;

import java.util.List;

import com.ECom.ecommerce.dtos.item_type.request.ItemTypeRequest;
import com.ECom.ecommerce.dtos.item_type.response.ItemTypeResponse;
import com.ECom.ecommerce.entities.Brand;
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.Product;


public interface ItemTypeService {

    List<ItemTypeResponse> findByName(String name);

    ItemTypeResponse addItemType(ItemTypeRequest itemTypeRequest);

    ItemTypeResponse updateItemType(Long itemTypeId, ItemTypeRequest itemTypeRequest);

    void deleteItemType(Long itemTypeId);

    boolean existsByName(String name);

    void deactivate(Long itemTypeId);
    
    void activate(Long itemTypeId);

    List<ItemTypeResponse> findAll();
    
    ItemTypeResponse findByProduct(Product product);

    List<ItemTypeResponse> findByCategoryId(Category category);
    
    List<ItemTypeResponse> findByBrandId(Brand brand);



    
}
