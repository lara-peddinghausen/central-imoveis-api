package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Pessoa> cadastrar(@RequestBody @jakarta.validation.Valid DadosCadastroPessoa dados) {
        var pessoa = new Pessoa(dados);
        pessoaRepository.save(pessoa);

        // Devolve a pessoa com o ID gerado pelo banco para o Front-end
        return ResponseEntity.status(201).body(pessoa);
    }

    @GetMapping
    public Page<DadosListagemPessoa> listarPorPagina(Pageable paginacao) {
        return pessoaRepository.findAll(paginacao).map(DadosListagemPessoa::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Integer id) {
        return pessoaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Pessoa> atualizar(@RequestBody DadosAtualizaPessoa dados) {
        // Mudamos para findById para carregar os dados de forma segura
        var pessoa = pessoaRepository.findById(dados.id())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Inquilino não encontrado"));

        pessoa.atualizarPessoa(dados);

        // Retorna HTTP 200 OK com o objeto atualizado
        return ResponseEntity.ok(pessoa);
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
    // var pessoa = pessoaRepository.getReferenceById(id);
    // pessoa.exclusaoLogica();
    // }
}
