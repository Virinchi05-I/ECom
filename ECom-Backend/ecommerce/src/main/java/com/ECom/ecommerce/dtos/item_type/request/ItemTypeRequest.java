package com.ECom.ecommerce.dtos.item_type.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemTypeRequest {
    
    private String itemTypeName;
    private boolean active;
}
