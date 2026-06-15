package com.centraldeimoveis.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "E-mail é obrigatório") 
    @Email(message = "Formato de e-mail inválido") // Adicionado validação de formato
    String email,
    
    @NotBlank(message = "Senha é obrigatória") 
    String senha
) {}
