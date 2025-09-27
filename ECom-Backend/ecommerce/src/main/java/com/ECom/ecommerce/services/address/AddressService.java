package com.ECom.ecommerce.services.address;

import java.util.List;

import com.ECom.ecommerce.dtos.address.request.CreateAddressRequest;
import com.ECom.ecommerce.dtos.address.request.UpdateAddressRequest;
import com.ECom.ecommerce.dtos.address.response.AddressResponse;

public interface AddressService {
    
    // --------------- USER -----------------\\

    List<AddressResponse> findByUserId(Long userId);
    
    AddressResponse create(CreateAddressRequest createAddressRequest);
    
    AddressResponse update(UpdateAddressRequest updateAddressRequest);

    void deleteById(Long addressId);

    void deleteByUserId(Long userId, Long addressId);
    
    // --------------- ADMIN -----------------\\
    
    List<AddressResponse> findAll();
    
    AddressResponse findById(Long addressId);
    
} 