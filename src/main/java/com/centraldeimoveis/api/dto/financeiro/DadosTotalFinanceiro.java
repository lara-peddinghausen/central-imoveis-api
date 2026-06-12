package com.centraldeimoveis.api.dto.financeiro;

import java.math.BigDecimal;

public record DadosTotalFinanceiro(
    BigDecimal totalReceitas,
    BigDecimal totalDespesas,
    BigDecimal saldo // Opcional: receitas - despesas 
) {}