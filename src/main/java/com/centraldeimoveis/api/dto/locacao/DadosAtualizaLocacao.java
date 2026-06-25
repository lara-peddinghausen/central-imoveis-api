package com.centraldeimoveis.api.dto.locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.centraldeimoveis.api.model.locacao.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

public record DadosAtualizaLocacao(
    Integer id,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataTermino,

    BigDecimal aluguel,

    String observacao,

    Status status, 

    Integer pessoa
) {

}
