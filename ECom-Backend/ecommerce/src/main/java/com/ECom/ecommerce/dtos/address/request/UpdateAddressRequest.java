package com.ECom.ecommerce.dtos.address.request;

import com.ECom.ecommerce.entities.AddressType;
import com.ECom.ecommerce.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {
    
    private Long id;
    private Long userId;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private AddressType addressType;
}
