package com.ECom.ecommerce.dtos.user.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDate dateOfBirth;
    private boolean active;
    private boolean emailVerified;
    private boolean phoneVerified;
}
