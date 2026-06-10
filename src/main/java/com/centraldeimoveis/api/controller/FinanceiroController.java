package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.centraldeimoveis.api.model.financeiro.DadosAtualizaFinanceiro;
import com.centraldeimoveis.api.model.financeiro.DadosCadastroFinanceiro;
import com.centraldeimoveis.api.model.financeiro.DadosListagemFinanceiro;
import com.centraldeimoveis.api.model.financeiro.Financeiro;
import com.centraldeimoveis.api.model.financeiro.FinanceiroRepository;
import com.centraldeimoveis.api.model.imovel.ImovelRepository;
import com.centraldeimoveis.api.model.financeiro.FinanceiroService;
import com.centraldeimoveis.api.model.financeiro.DadosFluxoFinanceiroDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/financeiro")
@CrossOrigin(origins = "*")
public class FinanceiroController {

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private FinanceiroService financeiroService;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroFinanceiro dados) {
        var imovel = imovelRepository.getReferenceById(dados.imovel());
        var financeiro = new Financeiro(dados, imovel);
        financeiroRepository.save(financeiro);
    }

    @GetMapping
    public Page<DadosListagemFinanceiro> listarPorPagina(Pageable paginacao) {
        return financeiroRepository.findAll(paginacao).map(DadosListagemFinanceiro::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaFinanceiro dados) {
        var financeiro = financeiroRepository.getReferenceById(dados.id());
        financeiro.atualizarFinanceiro(dados);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        financeiroRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    // var financeiro = financeiroRepository.getReferenceById(id);
    // financeiro.exclusaoLogica();
    // }

    // Rota corrigida para não usar o "path" duplicado diretamente e sim mapear um
    // sub-recurso se necessário,
    // mas mantida na mesma ordem estrutural que você enviou.
    @PostMapping("/movimentacao") 
    @Transactional
    public void cadastrarMovimentacao(@RequestBody @Valid DadosCadastroFinanceiro dados) {
        
        // Chama a Service que executa as validações e salva no PostgreSQL
        financeiroService.registrarMovimentacao(dados);
        
        // Sem return! O Spring envia automaticamente o status 200 OK para o celular
    }

    // Rota que alimenta o gráfico do React Native (visto na tabela de rotas do PDF)
    @GetMapping("/{id}/financeiro/fluxo")
    public DadosFluxoFinanceiroDTO visualizarFluxoFinanceiro(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "mensal") String visualizacao) {

        // Retorna o objeto direto. O Spring transforma em JSON e envia com status 200
        // OK
        return financeiroService.calcularFluxoGrafico(id, visualizacao);
    }

}
