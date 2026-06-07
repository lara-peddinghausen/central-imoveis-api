package com.centraldeimoveis.api.model.locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.pessoa.Pessoa;

public record DadosListagemLocacao(
    Integer id,
    Status status,
    LocalDate dataInicio,
    LocalDate dataTermino,
    BigDecimal aluguel,
    String observacao,
    Imovel imovel,
    Pessoa pessoa
) {
    public DadosListagemLocacao(Locacao locacao) {
        this(locacao.getId(), locacao.getStatus(), locacao.getDataInicio(), locacao.getDataTermino(), locacao.getAluguel(), locacao.getObservacao(), locacao.getImovel(), locacao.getPessoa());
    }
}
