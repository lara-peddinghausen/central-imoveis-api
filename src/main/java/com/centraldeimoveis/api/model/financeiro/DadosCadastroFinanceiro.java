package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroFinanceiro(

    @NotNull
    TipoMovimentacao tipoMovimentacao,

    @NotNull
    BigDecimal valor,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data,

    String descricao,

    @NotNull
    Integer imovel

) {

}
