package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;
import java.util.List;

public record DadosFluxoFinanceiroDTO(
    List<String> labels,       // ["Jan", "Fev", "Mar", ...]
    List<BigDecimal> receitas, 
    List<BigDecimal> despesas  
) {
}
