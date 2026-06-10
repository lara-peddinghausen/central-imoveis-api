package com.centraldeimoveis.api.model.financeiro;

import com.centraldeimoveis.api.model.imovel.ImovelRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FinanceiroService {

    // Dependências declaradas como final (Imutabilidade)
    private final FinanceiroRepository financeiroRepository;
    private final ImovelRepository imovelRepository;

    // Injeção via Construtor (Boa prática recomendada pelo Spring e essencial para
    // testes com JUnit)
    public FinanceiroService(FinanceiroRepository financeiroRepository, ImovelRepository imovelRepository) {
        this.financeiroRepository = financeiroRepository;
        this.imovelRepository = imovelRepository;
    }

    @Transactional // Garante a consistência da transação no PostgreSQL
    public Financeiro registrarMovimentacao(DadosCadastroFinanceiro dados) {
        // 1. REGRA DE NEGÓCIO: Verifica se o imóvel existe
        var imovel = imovelRepository.findById(dados.imovel())
                .orElseThrow(() -> new ValidationException("Imóvel não encontrado!"));

        // 2. LÓGICA DE CÁLCULO / VALIDAÇÃO FINANCEIRA
        if ("SAIDA".equals(dados.tipoMovimentacao())) {
            if (dados.valor().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("O valor de uma despesa deve ser maior que zero!");
            }

            // Correção de segurança: Evita NullPointerException se a descrição vier nula ou
            // em branco
            if (dados.valor().compareTo(new BigDecimal("1000.00")) > 0 &&
                    (dados.descricao() == null || dados.descricao().trim().isEmpty())) {
                throw new ValidationException("Despesas acima de R$1.000,00 exigem uma descrição descritiva!");
            }
        }

        // 3. INSTANCIAÇÃO LIMPA E SALVAMENTO
        // Passamos os dados e a entidade resolvida para o construtor da própria classe
        // Financeiro
        var novaMovimentacao = new Financeiro(dados, imovel);

        return financeiroRepository.save(novaMovimentacao);
    }

    public DadosFluxoFinanceiroDTO calcularFluxoGrafico(Integer idImovel, String visualizacao) {
        // 1. Valida se o imóvel existe antes de computar
        if (!imovelRepository.existsById(idImovel)) {
            throw new ValidationException("Imóvel não encontrado para gerar o fluxo!");
        }

        // 2. Busca os dados reais e brutos do banco de dados
        List<DadosAgrupadosMes> receitasDoBanco = financeiroRepository.buscarReceitasAgrupadas(idImovel);
        List<DadosAgrupadosMes> despesasDoBanco = financeiroRepository.buscarDespesasAgrupadas(idImovel);

        // 3. Cria listas fixas com tamanho 12 preenchidas com ZERO
        // (Garante que os 12 meses apareçam alinhados no gráfico do React Native)
        List<BigDecimal> receitas = new java.util.ArrayList<>(java.util.Collections.nCopies(12, BigDecimal.ZERO));
        List<BigDecimal> despesas = new java.util.ArrayList<>(java.util.Collections.nCopies(12, BigDecimal.ZERO));

        // 4. Distribui os valores das receitas vindas do banco nos meses
        // correspondentes
        // O banco retorna o mês de 1 a 12, por isso usamos (mes - 1) para encaixar no
        // índice do Array (0 a 11)
        for (DadosAgrupadosMes item : receitasDoBanco) {
            int indiceMes = item.getMes() - 1;
            receitas.set(indiceMes, item.getTotal());
        }

        // 5. Distribui os valores das despesas nos meses correspondentes
        for (DadosAgrupadosMes item : despesasDoBanco) {
            int indiceMes = item.getMes() - 1;
            despesas.set(indiceMes, item.getTotal());
        }

        // 6. Lista fixa de etiquetas para o eixo X do gráfico
        List<String> labels = List.of("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov",
                "Dez");

        // Retorna o DTO perfeitamente montado e com dados dinâmicos reais!
        return new DadosFluxoFinanceiroDTO(labels, receitas, despesas);
    }

    @Transactional(readOnly = true)
    public DadosTotalFinanceiroDTO obterTotaisGerais(Integer idImovel) {
        // 1. Valida se o imóvel existe
        if (!imovelRepository.existsById(idImovel)) {
            throw new ValidationException("Imóvel não encontrado!");
        }

        // 2. Busca os totais direto do banco
        BigDecimal totalReceitas = financeiroRepository.obterTotalReceitasAno(idImovel);
        BigDecimal totalDespesas = financeiroRepository.obterTotalDespesasAno(idImovel);

        // 3. Calcula o saldo final
        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        // 4. Retorna os dados prontos para os cards do frontend
        return new DadosTotalFinanceiroDTO(totalReceitas, totalDespesas, saldo);
    }
}