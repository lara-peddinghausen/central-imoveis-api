package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Integer> {
    // Busca e soma as ENTRADAS do imóvel agrupadas pelo mês do ano corrente
    @Query(value = """
            SELECT EXTRACT(MONTH FROM f.data) as mes, SUM(f.valor) as total
            FROM financeiro f
            WHERE f.imovel_id = :idImovel
              AND f.tipo_movimentacao = 'ENTRADA'
              AND EXTRACT(YEAR FROM f.data) = EXTRACT(YEAR FROM CURRENT_DATE)
            GROUP BY EXTRACT(MONTH FROM f.data)
            ORDER BY mes
            """, nativeQuery = true)
    List<DadosAgrupadosMes> buscarReceitasAgrupadas(@Param("idImovel") Integer idImovel);

    // Busca e soma as SAÍDAS do imóvel agrupadas pelo mês do ano corrente
    @Query(value = """
            SELECT EXTRACT(MONTH FROM f.data) as mes, SUM(f.valor) as total
            FROM financeiro f
            WHERE f.imovel_id = :idImovel
              AND f.tipo_movimentacao = 'SAIDA'
              AND EXTRACT(YEAR FROM f.data) = EXTRACT(YEAR FROM CURRENT_DATE)
            GROUP BY EXTRACT(MONTH FROM f.data)
            ORDER BY mes
            """, nativeQuery = true)
    List<DadosAgrupadosMes> buscarDespesasAgrupadas(@Param("idImovel") Integer idImovel);

    // Busca a soma TOTAL de ENTRADAS do imóvel no ano corrente
    @Query(value = """
            SELECT COALESCE(SUM(f.valor), 0)
            FROM financeiro f
            WHERE f.imovel_id = :idImovel
              AND f.tipo_movimentacao = 'ENTRADA'
              AND EXTRACT(YEAR FROM f.data) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    BigDecimal obterTotalReceitasAno(@Param("idImovel") Integer idImovel);

    // Busca a soma TOTAL de SAÍDAS do imóvel no ano corrente
    @Query(value = """
            SELECT COALESCE(SUM(f.valor), 0)
            FROM financeiro f
            WHERE f.imovel_id = :idImovel
              AND f.tipo_movimentacao = 'SAIDA'
              AND EXTRACT(YEAR FROM f.data) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    BigDecimal obterTotalDespesasAno(@Param("idImovel") Integer idImovel);
}
