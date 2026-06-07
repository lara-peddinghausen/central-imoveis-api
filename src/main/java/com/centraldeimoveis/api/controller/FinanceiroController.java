package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.model.financeiro.DadosAtualizaFinanceiro;
import com.centraldeimoveis.api.model.financeiro.DadosCadastroFinanceiro;
import com.centraldeimoveis.api.model.financeiro.DadosListagemFinanceiro;
import com.centraldeimoveis.api.model.financeiro.Financeiro;
import com.centraldeimoveis.api.model.financeiro.FinanceiroRepository;
import com.centraldeimoveis.api.model.imovel.ImovelRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Autowired
    private ImovelRepository imovelRepository;

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
    //     var financeiro = financeiroRepository.getReferenceById(id);
    //     financeiro.exclusaoLogica();
    // }

}

