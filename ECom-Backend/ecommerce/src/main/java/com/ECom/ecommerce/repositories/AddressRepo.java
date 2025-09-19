package com.ECom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECom.ecommerce.entities.Address;
import com.ECom.ecommerce.entities.User;

public interface AddressRepo extends JpaRepository<Address, String> {

    List<Address> findByUser(User user);

    void deleteByUser(User user);

    List<Address> findByStreet(String street);

    List<Address> findByCity(String city);
    
    List<Address> findByState(String state);
    
    List<Address> findByCountry(String country);

    List<Address> findByUserAndCity(User user, String city);

    List<Address> findByUserAndState(User user, String state);
    
    List<Address> findByUserAndCountry(User user, String country);
    
    List<Address> findByUserAndStreet(User user, String street);
}
