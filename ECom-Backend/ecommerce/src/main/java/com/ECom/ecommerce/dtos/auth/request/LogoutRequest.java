package com.ECom.ecommerce.dtos.auth.request;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(

    @NotBlank(message = "Refresh token is required")
    String refreshToken
) {}
