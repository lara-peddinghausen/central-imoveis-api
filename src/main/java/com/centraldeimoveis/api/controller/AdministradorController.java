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

import com.centraldeimoveis.api.model.administrador.Administrador;
import com.centraldeimoveis.api.model.administrador.AdministradorRepository;
import com.centraldeimoveis.api.model.administrador.DadosAtualizaAdministrador;
import com.centraldeimoveis.api.model.administrador.DadosCadastroAdministrador;
import com.centraldeimoveis.api.model.administrador.DadosListagemAdministrador;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;
    
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroAdministrador dados) {
        administradorRepository.save(new Administrador(dados));
    }

    @GetMapping
    public Page<DadosListagemAdministrador> listarPorPagina(Pageable paginacao) {
        return administradorRepository.findAll(paginacao).map(DadosListagemAdministrador::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaAdministrador dados) {
        var admnistrador = administradorRepository.getReferenceById(dados.id());
        admnistrador.atualizarAdministrador(dados);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        administradorRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    //     var administrador = administradorRepository.getReferenceById(id);
    //     administrador.exclusaoLogica();
    // }
}
