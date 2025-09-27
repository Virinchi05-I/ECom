package com.ECom.ecommerce.dtos.user.request;

import java.time.LocalDate;

import com.ECom.ecommerce.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private boolean active;
    private boolean emailVerified;
    private boolean phoneVerified;
}
