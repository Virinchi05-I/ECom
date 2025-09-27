package com.ECom.ecommerce.services.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.address.request.CreateAddressRequest;
import com.ECom.ecommerce.dtos.address.request.UpdateAddressRequest;
import com.ECom.ecommerce.dtos.address.response.AddressResponse;
import com.ECom.ecommerce.entities.Address;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.AddressRepo;
import com.ECom.ecommerce.repositories.UserRepo;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public AddressResponse create(CreateAddressRequest createAddressRequest) {
        
        Address address = new Address();
        address.setId(createAddressRequest.getId());
        address.setUser(userRepo.findById(createAddressRequest.getUserId()).orElse(null));
        address.setStreet(createAddressRequest.getStreet());
        address.setCity(createAddressRequest.getCity());
        address.setState(createAddressRequest.getState());
        address.setCountry(createAddressRequest.getCountry());
        address.setPincode(createAddressRequest.getPincode());
        address.setAddressType(createAddressRequest.getAddressType());

        Address savedAddress = addressRepo.save(address);
        
        return mapAddressToResponse(savedAddress);
    }
    
    @Override
    public AddressResponse update(UpdateAddressRequest updateAddressRequest) {
        
        // 1. Find the existing address
    Address address = addressRepo.findById(updateAddressRequest.getId())
            .orElseThrow(() -> new RuntimeException("Address not found"));

    // 2. Update fields only if not null
    if (updateAddressRequest.getUserId() != null) {
        address.setUser(userRepo.findById(updateAddressRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    if (updateAddressRequest.getStreet() != null) {
        address.setStreet(updateAddressRequest.getStreet());
    }
    if (updateAddressRequest.getCity() != null) {
        address.setCity(updateAddressRequest.getCity());
    }
    if (updateAddressRequest.getState() != null) {
        address.setState(updateAddressRequest.getState());
    }
    if (updateAddressRequest.getCountry() != null) {
        address.setCountry(updateAddressRequest.getCountry());
    }
    if (updateAddressRequest.getPincode() != null) {
        address.setPincode(updateAddressRequest.getPincode());
    }
    if (updateAddressRequest.getAddressType() != null) {
        address.setAddressType(updateAddressRequest.getAddressType());
    }
        Address savedAddress = addressRepo.save(address);
        
        return mapAddressToResponse(savedAddress);
    }
    
    @Override
    public void deleteById(Long addressId) {
        
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        addressRepo.delete(address);
    }
    
    @Override
    public void deleteByUserId(Long userId, Long addressId) {
        
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        
        if (!address.getUser().getId().equals(userId)) {
            throw new SecurityException("User not authorized to delete this address");
        }

        addressRepo.delete(address);
    }
    
    @Override
    public List<AddressResponse> findByUserId(Long userId) {
       List<Address> addresses = addressRepo.findByUserId(userId);
        
        return addresses.stream().map(this::mapAddressToResponse).toList();
    }

    @Override
    public List<AddressResponse> findAll() {
        
        List<Address> addresses = addressRepo.findAll();
        
        return addresses.stream().map(this::mapAddressToResponse).toList();
    }

    @Override
    public AddressResponse findById(Long addressId) {
        
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        return mapAddressToResponse(address);
    }

    private AddressResponse mapAddressToResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setUserId(address.getUser() != null ? address.getUser().getId() : null);
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPincode(address.getPincode());
        response.setAddressType(address.getAddressType());

        return response;
    }
     
    
}
