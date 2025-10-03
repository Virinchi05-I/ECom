package com.ECom.ecommerce.services.user;

import java.util.*;

import com.ECom.ecommerce.dtos.user.request.UpdateUserRequest;
import com.ECom.ecommerce.dtos.user.request.UserRequest;
import com.ECom.ecommerce.dtos.user.response.UserResponse;
import com.ECom.ecommerce.entities.Role;

public interface    UserService {

    UserResponse createUser(UserRequest userRequest, String role);

    UserResponse updateUser(Long userId, UpdateUserRequest updateUserserRequest);

    void deleteUser(Long userId);

    UserResponse getUserById(Long userId); 
    Optional<UserResponse> getUserByEmail(String email);
    Optional<UserResponse> getUserByUsername(String username);
    Optional<UserResponse> getUserByPhoneNumber(String phoneNumber);

    UserResponse setUserRole(Long userId, String role);

    List<UserResponse> findAll();
    List<UserResponse> searchUsers(String name, String email, String phoneNumber, String role);

    UserResponse setActiveStatus(Long userId, boolean active);
    UserResponse setVerification(Long userId, boolean emailVerified, boolean phoneVerified);

}
