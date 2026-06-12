package com.centraldeimoveis.api.dto.administrador;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record DadosCadastroAdministrador(

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String senha,

    @Email(message = "Formato de e-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    String email,

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser uma data passada")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento,

    @NotBlank(message = "CPF é obrigatório")
    String cpf
) {
    
}
