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

import com.centraldeimoveis.api.model.proprietario.DadosAtualizaProprietario;
import com.centraldeimoveis.api.model.proprietario.DadosCadastroProprietario;
import com.centraldeimoveis.api.model.proprietario.DadosListagemProprietario;
import com.centraldeimoveis.api.model.proprietario.Proprietario;
import com.centraldeimoveis.api.model.proprietario.ProprietarioRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroProprietario dados) {
        proprietarioRepository.save(new Proprietario(dados));
    }

    @GetMapping
    public Page<DadosListagemProprietario> listarPorPagina(Pageable paginacao) {
        return proprietarioRepository.findAll(paginacao).map(DadosListagemProprietario::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaProprietario dados) {
        var admnistrador = proprietarioRepository.getReferenceById(dados.id());
        admnistrador.atualizarProprietario(dados);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        proprietarioRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    //     var proprietario = proprietarioRepository.getReferenceById(id);
    //     proprietario.exclusaoLogica();
    // }

}
