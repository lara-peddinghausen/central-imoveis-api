package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;

public record DadosTotalFinanceiroDTO(
    BigDecimal totalReceitas,
    BigDecimal totalDespesas,
    BigDecimal saldo // Opcional: receitas - despesas 
) {}