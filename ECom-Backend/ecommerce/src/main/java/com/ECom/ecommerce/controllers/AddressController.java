package com.ECom.ecommerce.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.address.request.CreateAddressRequest;
import com.ECom.ecommerce.dtos.address.request.UpdateAddressRequest;
import com.ECom.ecommerce.dtos.address.response.AddressResponse;
import com.ECom.ecommerce.services.address.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@CrossOrigin
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/filterById")
    public ResponseEntity<List<AddressResponse>> getByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(addressService.findByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<AddressResponse> addAddress(@RequestBody CreateAddressRequest createAddressRequest) {
        return ResponseEntity.ok(addressService.create(createAddressRequest));
    }

    @PatchMapping("/update")
    public ResponseEntity<AddressResponse> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) {
        return ResponseEntity.ok(addressService.update(updateAddressRequest));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteAddressById(@RequestParam("addressId") Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByUserId")
    public ResponseEntity<Void> deleteAddressByUserId(
        @RequestParam("userId") Long userId, 
        @RequestParam("addressId") Long addressId
        ) {
        addressService.deleteByUserId(userId, addressId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AddressResponse>> getAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/getById")
    public ResponseEntity<AddressResponse> getById(@RequestParam("addressId") Long addressId) {
        return ResponseEntity.ok(addressService.findById(addressId));
    }
}
