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

import com.centraldeimoveis.api.dto.locacao.DadosAtualizaLocacao;
import com.centraldeimoveis.api.dto.locacao.DadosCadastroLocacao;
import com.centraldeimoveis.api.dto.locacao.DadosListagemLocacao;
import com.centraldeimoveis.api.model.locacao.Locacao;
import com.centraldeimoveis.api.model.pessoa.Pessoa;
import com.centraldeimoveis.api.repository.ImovelRepository;
import com.centraldeimoveis.api.repository.LocacaoRepository;
import com.centraldeimoveis.api.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Locacao> cadastrar(@RequestBody @jakarta.validation.Valid DadosCadastroLocacao dados) {
        // Busca o Imóvel obrigatoriamente usando o ID mapeado
        var imovel = imovelRepository.getReferenceById(dados.imovel());

        // Altera o status do imóvel para ALUGADO automaticamente
        imovel.setStatus(com.centraldeimoveis.api.model.imovel.Status.ALUGADO);

        // Busca a Pessoa apenas se enviada (inquilino)
        com.centraldeimoveis.api.model.pessoa.Pessoa pessoa = null;
        if (dados.pessoa() != null) {
            pessoa = pessoaRepository.getReferenceById(dados.pessoa());
        }

        // Cria e salva a locação
        var locacao = new Locacao(dados, imovel, pessoa);
        locacaoRepository.save(locacao);

        // Retorna o 201 Created com os dados atualizados
        return ResponseEntity.status(201).body(locacao);
    }

    @GetMapping
    public Page<DadosListagemLocacao> listarPorPagina(Pageable paginacao) {
        return locacaoRepository.findAll(paginacao).map(DadosListagemLocacao::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Locacao> atualizar(@RequestBody @jakarta.validation.Valid DadosAtualizaLocacao dados) {
        // Busca a locação atual
        var locacao = locacaoRepository.getReferenceById(dados.id());

        // Busca a pessoa encontrada pelo ID enviado
        Pessoa pessoa = null;
        if (dados.pessoa() != null) {
            pessoa = pessoaRepository.getReferenceById(dados.pessoa());
        }

        // Modifica os atributos na entidade
        locacao.atualizarLocacao(dados, pessoa);

        // Força o Hibernate a gravar as alterações da transação
        locacaoRepository.save(locacao);

        return ResponseEntity.ok(locacao);
    }

    // Exclusão
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        locacaoRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    // var locacao = locacaoRepository.getReferenceById(id);
    // locacao.exclusaoLogica();
    // }

}
