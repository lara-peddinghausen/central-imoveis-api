package com.centraldeimoveis.api.model.pessoa;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

public record DadosAtualizaPessoa(
    Integer id,

    @Email 
    String email,

    String nome,
    String senha,

    @Past(message = "A data de nascimento deve ser uma data passada") 
    @JsonFormat(pattern = "dd/MM/yyyy") 
    LocalDate dataNascimento,

    String telefone
) {

}
