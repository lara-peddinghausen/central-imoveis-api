package com.centraldeimoveis.api.model.administrador;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;

public record DadosAtualizacaoAdministrador(
    Integer id,
    String nome,
    String senha,

    @Past(message = "A data de nascimento deve ser uma data passada")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento
) {

}
