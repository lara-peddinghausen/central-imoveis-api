package com.centraldeimoveis.api.model.locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroLocacao(

    Status status,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataInicio,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataTermino,

    @NotNull
    BigDecimal aluguel,

    String observacao,

    @NotNull
    Integer imovel,

    @NotNull
    Integer pessoa

) {

}
