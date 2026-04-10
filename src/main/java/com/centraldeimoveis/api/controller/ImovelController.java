package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.model.administrador.AdministradorRepository;
import com.centraldeimoveis.api.model.imovel.DadosCadastroImovel;
import com.centraldeimoveis.api.model.imovel.DadosListagemImovel;
import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.imovel.ImovelRepository;

import lombok.var;

@RestController
@RequestMapping("/imovel")
public class ImovelController {
    
    @Autowired
    private ImovelRepository repository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroImovel dados) {
        var administrador = administradorRepository.getReferenceById(dados.administrador());
        var imovel = new Imovel(dados, administrador);
        repository.save(imovel);
    }

    @GetMapping
    public Page<DadosListagemImovel> listarImoveis(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemImovel::new);
    }

}
