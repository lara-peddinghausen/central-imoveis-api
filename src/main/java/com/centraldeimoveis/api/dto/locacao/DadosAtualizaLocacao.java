package com.centraldeimoveis.api.dto.locacao;

import java.math.BigDecimal;

import com.centraldeimoveis.api.model.locacao.Status;


public record DadosAtualizaLocacao(
    Integer id,

    String observacao,

    Status status, 

    BigDecimal aluguel,

    Integer pessoa
) {

}
