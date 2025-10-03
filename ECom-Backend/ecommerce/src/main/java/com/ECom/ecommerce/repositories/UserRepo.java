package com.ECom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    // -------- Customer & Seller utility methods -------- //
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
    
    Optional<User> findByName(String name);
    
    List<User> findByDateOfBirth(LocalDate dateOfBirth);

    // Optional<User> findByDateOfBirth(LocalDate dateOfBirth); --> multiple user can search by their date of birth Optinal is used for safety 
    // Optional<User> findByEmailAndPassword(String email, String password); ---> not recommended

    // -------- Admin utility methods --------- //
    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% AND u.email LIKE %:email% AND u.phoneNumber LIKE %:phoneNumber% AND u.role = :role")
    List<User> searchUsers(@Param("name") String name, @Param("email") String email,
                       @Param("phoneNumber") String phoneNumber, @Param("role") Role role);

    List<User> findByActiveAndRole(boolean active, Role role);

    List<User> findByCreatedDate(LocalDateTime createdDate);
    
    List<User> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    

}
