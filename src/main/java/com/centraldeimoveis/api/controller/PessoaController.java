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

import com.centraldeimoveis.api.dto.pessoa.DadosAtualizaPessoa;
import com.centraldeimoveis.api.dto.pessoa.DadosCadastroPessoa;
import com.centraldeimoveis.api.dto.pessoa.DadosListagemPessoa;
import com.centraldeimoveis.api.model.pessoa.Pessoa;
import com.centraldeimoveis.api.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    
        @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPessoa dados) {
        pessoaRepository.save(new Pessoa(dados));
    }

    @GetMapping
    public Page<DadosListagemPessoa> listarPorPagina(Pageable paginacao) {
        return pessoaRepository.findAll(paginacao).map(DadosListagemPessoa::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaPessoa dados) {
        var admnistrador = pessoaRepository.getReferenceById(dados.id());
        admnistrador.atualizarPessoa(dados);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        pessoaRepository.deleteById(id);
    }

    // Exclusão lógica 
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    //     var pessoa = pessoaRepository.getReferenceById(id);
    //     pessoa.exclusaoLogica();
    // }
}
