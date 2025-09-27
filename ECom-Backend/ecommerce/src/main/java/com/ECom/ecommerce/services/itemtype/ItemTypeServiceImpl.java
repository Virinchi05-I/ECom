package com.ECom.ecommerce.services.itemtype;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.item_type.request.ItemTypeRequest;
import com.ECom.ecommerce.dtos.item_type.response.ItemTypeResponse;
import com.ECom.ecommerce.entities.Brand;
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.repositories.ItemTypeRepo;
import com.ECom.ecommerce.entities.ItemType;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

    @Autowired
    private ItemTypeRepo itemTypeRepo;
    @Override
    public List<ItemTypeResponse> findByName(String name) {
        
        List<ItemType> itemTypes = itemTypeRepo.findByName(name);
        
        
        return itemTypes.stream().map(this::mapItemTypeToRespone).toList();
        
    }

    @Override
    public ItemTypeResponse addItemType(ItemTypeRequest itemTypeRequest) {
        
        ItemType itemType = new ItemType();
        itemType.setItemTypeName(itemTypeRequest.getItemTypeName());
        itemType.setActive(itemTypeRequest.isActive());
        
        itemTypeRepo.save(itemType);
        
        return mapItemTypeToRespone(itemType);
    }

    @Override
    public ItemTypeResponse updateItemType(Long itemTypeId, ItemTypeRequest itemTypeRequest) {
        
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
                .orElseThrow(() -> new RuntimeException("Item Type not found"));

        if(itemTypeRequest.getItemTypeName() != null) itemType.setItemTypeName(itemTypeRequest.getItemTypeName());
        if(itemTypeRequest.isActive() != Boolean.valueOf(null)) itemType.setActive(itemTypeRequest.isActive());
        
        itemTypeRepo.save(itemType);
        
        return mapItemTypeToRespone(itemType);
    }

    @Override
    public void deleteItemType(Long itemTypeId) {
        
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
                .orElseThrow(() -> new RuntimeException("Item Type not found"));

        itemTypeRepo.delete(itemType);
    }

    @Override
    public boolean existsByName(String name) {
        
        List<ItemType> itemType = itemTypeRepo.findByName(name);
        
        return !itemType.isEmpty();
    }

    @Override
    public void deactivate(Long itemTypeId) {
        
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
                .orElseThrow(() -> new RuntimeException("Item Type not found"));

        itemType.setActive(false);
        
        itemTypeRepo.save(itemType);
    }

    @Override
    public void activate(Long itemTypeId) {
       
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
                .orElseThrow(() -> new RuntimeException("Item Type not found"));

        itemType.setActive(true);
        
        itemTypeRepo.save(itemType);
    }

    @Override
    public List<ItemTypeResponse> findAll() {
        
        List<ItemType> itemTypes = itemTypeRepo.findAll();
        
        return itemTypes.stream().map(this::mapItemTypeToRespone).toList();
    }

    @Override
    public ItemTypeResponse findByProduct(Product product) {
        
        Optional<ItemType> itemType = itemTypeRepo.findByProduct(product);

        return mapItemTypeToRespone(itemType.get());
    }

    @Override
    public List<ItemTypeResponse> findByCategoryId(Category category) {
        List<ItemType> itemTypes = itemTypeRepo.findByCategory(category);

        return itemTypes.stream().map(this::mapItemTypeToRespone).toList();
    }

    @Override
    public List<ItemTypeResponse> findByBrandId(Brand brand) {
        List<ItemType> itemTypes = itemTypeRepo.findByBrand(brand);

        return itemTypes.stream().map(this::mapItemTypeToRespone).toList();
    }

    private ItemTypeResponse mapItemTypeToRespone(ItemType itemType){

        ItemTypeResponse itemTypeResponse = new ItemTypeResponse();
        itemTypeResponse.setItemTypeId(itemType.getItemTypeId());
        itemTypeResponse.setItemTypeName(itemType.getItemTypeName());
        itemTypeResponse.setActive(itemType.isActive());
        return itemTypeResponse;
    }
}
