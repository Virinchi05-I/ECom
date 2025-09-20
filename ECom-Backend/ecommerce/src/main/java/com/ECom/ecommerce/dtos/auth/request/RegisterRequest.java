package com.ECom.ecommerce.dtos.auth.request;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.entities.Role;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {


    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    
   public User toUserEntity(PasswordEncoder encoder) {
    
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.ROLE_GUEST);
        user.setActive(false);
        return user;
   }

}
