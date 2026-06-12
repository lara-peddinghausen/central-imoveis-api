package com.centraldeimoveis.api.dto.auth;

public record AuthResponse(
    String token,
    String email,
    String role
) {}
