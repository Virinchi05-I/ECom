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
    private Long id;

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
    private String emailVerificationToken;

    private boolean phoneVerified = false;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @JsonIgnore
    @OneToOne
    private Cart cart;

    // getters and setters

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    } 

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive(){
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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {

        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public void verifyEmailToken(String token){
        if(this.emailVerificationToken.equals(token)){
            this.emailVerified = true;
        }else{
            this.phoneVerified = true;
        }
    }


}