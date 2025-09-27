package com.ECom.ecommerce.services.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + username));

        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.getPassword()) // hashed password
            .authorities(user.getRole().name())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!user.isActive())
            .build();
    }

    

}
