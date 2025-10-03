package com.ECom.ecommerce.dtos.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    String email,
    String password
) {} // <<<<---- Java record constructor ---->>>>>> 