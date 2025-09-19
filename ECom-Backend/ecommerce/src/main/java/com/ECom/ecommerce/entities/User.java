package com.ECom.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;



@Entity 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email address")
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;
    
    @JsonIgnore
    @Column(nullable = false)
    @Size(min = 8, max = 16)
    private String password;

    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private boolean active = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_GUEST;

    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToOne
    private Cart cart;

    // getters and setters

    private boolean isActive(){
        return this.active;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNumber() {
    return phoneNumber;
}

    public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber != null && phoneNumber.matches("\\d{10}")) {
        this.phoneNumber = phoneNumber;
    } else {
        throw new IllegalArgumentException("Phone number must be 10 digits");
    }
}

}
