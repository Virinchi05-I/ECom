package com.ECom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Address;
import com.ECom.ecommerce.entities.User;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    void deleteByUser(User user);

    // ------------- ADMIN ------------- \\

    List<Address> findByStreet(String street);

    List<Address> findByCity(String city);
    
    List<Address> findByState(String state);
    
    List<Address> findByCountry(String country);

    List<Address> findByPincode(String pincode);

    public List<Address> findByUserId(Long userId);
}
