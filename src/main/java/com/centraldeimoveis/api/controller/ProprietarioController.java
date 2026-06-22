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

        // Limpa o CPF tirando pontos e traços para testar o valor puro
        String cpfLimpo = dados.cpf().replaceAll("\\D", "");

        // Se o CPF já existir, não cadastra
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

    @GetMapping
    public Page<DadosListagemProprietario> listarPorPagina(Pageable paginacao) {
        return proprietarioRepository.findAll(paginacao).map(DadosListagemProprietario::new);
    }

    @GetMapping("/{id}") // Mapeia a rota GET /proprietario/{id}
    public ResponseEntity<Object> buscarPorId(@PathVariable Integer id) {
        // Verifica se o proprietário existe no banco de dados
        if (!proprietarioRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Proprietário não encontrado.");
        }
        
        // Busca a entidade e retorna os dados dela com status 200 OK
        var proprietario = proprietarioRepository.getReferenceById(id);
        return ResponseEntity.ok(proprietario);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaProprietario dados, @PathVariable Integer id) {

        var proprietario = proprietarioRepository.getReferenceById(id);
        proprietario.atualizarProprietario(dados);
    }

    @PutMapping("/{id}") 
    @Transactional
    public ResponseEntity<Object> atualizar(
            @PathVariable Integer id, // Captura o ID da URL
            @RequestBody @jakarta.validation.Valid DadosAtualizaProprietario dados) { // Recebe o JSON Puro

        if (!proprietarioRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Proprietário não encontrado.");
        }

        var proprietario = proprietarioRepository.getReferenceById(id);
        proprietario.atualizarProprietario(dados);

        return ResponseEntity.ok(proprietario);
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
