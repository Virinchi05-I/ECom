package com.ECom.ecommerce.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.user.request.UpdateUserRequest;
import com.ECom.ecommerce.dtos.user.request.UserRequest;
import com.ECom.ecommerce.dtos.user.response.UserResponse;
import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
     
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(UserRequest userRequest, String role) {
        
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));


        user.setRole(Role.valueOf(role.toUpperCase()));

        user.setActive(userRequest.isActive());
        user.setEmailVerified(userRequest.getEmailVerified());
        user.setPhoneVerified(userRequest.getPhoneVerified());
        
        User savedUser = userRepo.save(user);
        
        return mapToResponse(savedUser);
    }
      

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateUserRequest.getName() != null) user.setName(updateUserRequest.getName());
        if (updateUserRequest.getEmail() != null) user.setEmail(updateUserRequest.getEmail());
        if (updateUserRequest.getPhoneNumber() != null) user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        if (updateUserRequest.getDateOfBirth() != null) user.setDateOfBirth(updateUserRequest.getDateOfBirth());

        if (updateUserRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        String roleStr = updateUserRequest.getRole().toString();
        if (roleStr != null && !roleStr.isEmpty()) {
            try {
                user.setRole(Role.valueOf(roleStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role: " + roleStr);
            }
        }

    User savedUser = userRepo.save(user);
        
    return mapToResponse(savedUser);
        
    }
    @Override
    public void deleteUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepo.delete(user);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return Optional.of(mapToResponse(user));
    }

    @Override
    public Optional<UserResponse> getUserByUsername(String Username) {
        User user = userRepo.findByName(Username)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        return Optional.of(mapToResponse(user));
    }

    @Override
    public Optional<UserResponse> getUserByPhoneNumber(String phoneNumber) {
        User user = userRepo.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() ->  new IllegalArgumentException("User not found"));
        
        return Optional.of(mapToResponse(user));
    }

    @Override
    public UserResponse setUserRole(Long userId, String role) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        user.setRole(Role.valueOf(role.toUpperCase()));
        User savedUser = userRepo.save(user);
        
        return mapToResponse(savedUser);       
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepo.findAll();

        return users.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<UserResponse> searchUsers(String name, String email, String phoneNumber, String role) {
        List<User> users = userRepo.searchUsers(name, email, phoneNumber, Role.valueOf(role.toUpperCase()));

        if (users.isEmpty()) {
        throw new IllegalArgumentException("No users found");
        }

        return users.stream().map(this::mapToResponse).toList();
    }
    

    @Override
    public UserResponse setActiveStatus(Long userId, boolean active) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    user.setActive(active);
    return mapToResponse(userRepo.save(user));
}

    @Override
    public UserResponse setVerification(Long userId, boolean emailVerified, boolean phoneVerified) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    if (emailVerified) user.setEmailVerified(true);
    if (phoneVerified) user.setPhoneVerified(true);
    return mapToResponse(userRepo.save(user));
}


    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setRole(user.getRole().toString());
        response.setActive(user.isActive());
        response.setEmailVerified(user.isEmailVerified());
        response.setPhoneVerified(user.isPhoneVerified());

        return response;
    }

}
