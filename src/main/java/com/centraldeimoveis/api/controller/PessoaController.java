package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.model.pessoa.DadosAtualizaPessoa;
import com.centraldeimoveis.api.model.pessoa.DadosCadastroPessoa;
import com.centraldeimoveis.api.model.pessoa.DadosListagemPessoa;
import com.centraldeimoveis.api.model.pessoa.Pessoa;
import com.centraldeimoveis.api.model.pessoa.PessoaRepository;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    
        @Autowired
    private PessoaRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPessoa dados) {
        repository.save(new Pessoa(dados));
    }

    @GetMapping
    public Page<DadosListagemPessoa> listarPorPagina(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemPessoa::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaPessoa dados) {
        var admnistrador = repository.getReferenceById(dados.id());
        admnistrador.atualizarPessoa(dados);
    }
}
