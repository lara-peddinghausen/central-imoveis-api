package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.centraldeimoveis.api.model.imovel.Imovel;

public record DadosListagemFinanceiro(
    Integer id,
    TipoMovimentacao tipoMovimentacao,
    BigDecimal valor,
    LocalDate data,
    String descricao,
    Imovel imovel
) {
    public DadosListagemFinanceiro(Financeiro financeiro) {
        this(financeiro.getId(), financeiro.getTipoMovimentacao(), financeiro.getValor(), financeiro.getData(), financeiro.getDescricao(), financeiro.getImovel());
    }
}
