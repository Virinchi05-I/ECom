package com.ECom.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.user.request.UpdateUserRequest;
import com.ECom.ecommerce.dtos.user.request.UserRequest;
import com.ECom.ecommerce.dtos.user.response.UserResponse;
import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.services.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ECom")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/user/create")
    public ResponseEntity<UserResponse> createUser(
        @RequestBody UserRequest userRequest,
        @RequestParam("role") String role
        ) {
        return ResponseEntity.ok(userService.createUser(userRequest, role));
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_VENDOR')")
    @PatchMapping("/user/update")
    public ResponseEntity<UserResponse> update(
        @RequestBody UpdateUserRequest updateUserRequest,
        @RequestParam("userId") Long userId
        ) {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> delete(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/deactivate")
    public ResponseEntity<Void> softDelete(@RequestParam Long userId){
        userService.setActiveStatus(userId, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/activate")
    public ResponseEntity<Void> restore(@RequestParam Long userId){
        userService.setActiveStatus(userId, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/filterById")
    public ResponseEntity<UserResponse> getById(@RequestParam Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/user/filterByUsername")
    public ResponseEntity<UserResponse> getByName(@RequestParam String username){
        
        Optional<UserResponse> response = userService.getUserByUsername(username);
        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/user/filterByEmail")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email){
        
        Optional<UserResponse> response = userService.getUserByEmail(email);
        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/filterByPhoneNumber")
    public ResponseEntity<UserResponse> getByPhoneNumber(@RequestParam String phoneNumber){
        
        Optional<UserResponse> response = userService.getUserByPhoneNumber(phoneNumber);
        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/admin/user/role")
    public ResponseEntity<UserResponse> updateRole(@RequestParam Long userId, @RequestParam String role){
        return ResponseEntity.ok(userService.setUserRole(userId, role));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user/all")
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user/filter")
    public ResponseEntity<List<UserResponse>> filter(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber,
        @RequestParam(required = false) String role
    ){
        return ResponseEntity.ok(userService.searchUsers(name, email, phoneNumber, role));
    }
} 
