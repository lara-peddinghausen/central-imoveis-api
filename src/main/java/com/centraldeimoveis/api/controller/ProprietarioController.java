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

import com.centraldeimoveis.api.model.proprietario.DadosAtualizaProprietario;
import com.centraldeimoveis.api.model.proprietario.DadosCadastroProprietario;
import com.centraldeimoveis.api.model.proprietario.DadosListagemProprietario;
import com.centraldeimoveis.api.model.proprietario.Proprietario;
import com.centraldeimoveis.api.model.proprietario.ProprietarioRepository;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroProprietario dados) {
        repository.save(new Proprietario(dados));
    }

    @GetMapping
    public Page<DadosListagemProprietario> listarPorPagina(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemProprietario::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaProprietario dados) {
        var admnistrador = repository.getReferenceById(dados.id());
        admnistrador.atualizarProprietario(dados);
    }

}
