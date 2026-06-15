package com.centraldeimoveis.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.dto.proprietario.DadosAtualizaProprietario;
import com.centraldeimoveis.api.dto.proprietario.DadosCadastroProprietario;
import com.centraldeimoveis.api.dto.proprietario.DadosListagemProprietario;
import com.centraldeimoveis.api.model.proprietario.Proprietario;
import com.centraldeimoveis.api.repository.ProprietarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @PostMapping
@Transactional
public ResponseEntity<Object> cadastrar(@RequestBody @Valid DadosCadastroProprietario dados) {
    
    // 1. Limpa o CPF tirando pontos e traços para testar o valor puro
    String cpfLimpo = dados.cpf().replaceAll("\\D", "");

    // 2. Regra de negócio: Se o CPF já existir, barra na hora!
    if (proprietarioRepository.existsByCpf(cpfLimpo)) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", "Este CPF já está cadastrado no sistema."));
    }

    // Se passou pelas validações, salva normalmente
    var proprietario = new Proprietario(dados);
    var salvo = proprietarioRepository.save(proprietario);
    
    return ResponseEntity.status(201).body(salvo);
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
    // var proprietario = proprietarioRepository.getReferenceById(id);
    // proprietario.exclusaoLogica();
    // }

}
