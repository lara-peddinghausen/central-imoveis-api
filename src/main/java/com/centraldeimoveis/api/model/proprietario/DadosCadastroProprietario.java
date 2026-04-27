package com.centraldeimoveis.api.model.proprietario;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record DadosCadastroProprietario(
    
    @Email
    String email,

    @NotBlank
    String nome,

    @NotNull
    @Past(message = "A data de nascimento deve ser uma data passada")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento,

    @NotNull
    @Valid
    String cpf,
    
    String telefone
) {
    
}
