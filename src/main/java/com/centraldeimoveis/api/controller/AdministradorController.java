package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.model.administrador.Administrador;
import com.centraldeimoveis.api.model.administrador.AdministradorRepository;
import com.centraldeimoveis.api.model.administrador.DadosAtualizacaAdministrador;
import com.centraldeimoveis.api.model.administrador.DadosCadastroAdministrador;
import com.centraldeimoveis.api.model.administrador.DadosListagemAdministrador;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorRepository repository;
    
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroAdministrador dados) {
        repository.save(new Administrador(dados));
    }

    @GetMapping
    public Page<DadosListagemAdministrador> listarPorPagina(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemAdministrador::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizacaAdministrador dados) {
        var admnistrador = repository.getReferenceById(dados.id());
        admnistrador.atualizarAdministrador(dados);
    }
}
