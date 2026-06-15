package com.centraldeimoveis.api.dto.auth;

public record AuthResponse(
    String token,
    String email,
    String role,
    Integer id,
    String nome,           // ◄ ADICIONADO
    String cpf,            // ◄ ADICIONADO
    String dataNascimento
) {}
