package com.ECom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.entities.User;

public interface UserRepo extends JpaRepository<User, String> {
    
    // -------- Customer & Seller utility methods -------- //
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
    
    Optional<User> findByName(String name);
    
    List<User> findByDateOfBirth(LocalDate dateOfBirth);

    // Optional<User> findByDateOfBirth(LocalDate dateOfBirth); --> multiple user can search by their date of birth Optinal is used for safety 
    // Optional<User> findByEmailAndPassword(String email, String password); ---> not recommended

    // -------- Admin utility methods --------- //
    List<User> findByRole(Role role);
    
    List<User> findByActiveAndRole(boolean active, Role role);

    List<User> findByCreatedDate(LocalDateTime createdDate);
    
    List<User> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
