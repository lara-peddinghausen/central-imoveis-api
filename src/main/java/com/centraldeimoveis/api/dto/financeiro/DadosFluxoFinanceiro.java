package com.centraldeimoveis.api.dto.financeiro;

import java.math.BigDecimal;
import java.util.List;

public record DadosFluxoFinanceiro(
    List<String> labels,       // ["Jan", "Fev", "Mar", ...]
    List<BigDecimal> receitas, 
    List<BigDecimal> despesas  
) {
}
