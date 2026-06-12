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

import com.centraldeimoveis.api.dto.imovel.DadosAtualizaImovel;
import com.centraldeimoveis.api.dto.imovel.DadosCadastroImovel;
import com.centraldeimoveis.api.dto.imovel.DadosListagemImovel;
import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.repository.AdministradorRepository;
import com.centraldeimoveis.api.repository.ImovelRepository;
import com.centraldeimoveis.api.repository.ProprietarioRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/imovel")
public class ImovelController {
    
    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroImovel dados) {
        var administrador = administradorRepository.getReferenceById(dados.administrador());
        var proprietario = proprietarioRepository.getReferenceById(dados.proprietario());
        var imovel = new Imovel(dados, administrador, proprietario);
        imovelRepository.save(imovel);
    }

    @GetMapping
    public Page<DadosListagemImovel> listarImoveis(Pageable paginacao) {
        return imovelRepository.findAll(paginacao).map(DadosListagemImovel::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaImovel dados) {
        var imovel = imovelRepository.getReferenceById(dados.id());
        imovel.atualizarImovel(dados);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        imovelRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    //     var imovel = imovelRepository.getReferenceById(id); 
    //     imovel.exclusaoLogica();
    // }

}
