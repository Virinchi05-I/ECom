package com.ECom.ecommerce.dtos.item_type.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemTypeResponse {

    private Long itemTypeId;
    private String itemTypeName;
    private boolean active;
    

}
