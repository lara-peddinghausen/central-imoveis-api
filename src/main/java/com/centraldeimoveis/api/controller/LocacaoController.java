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

import com.centraldeimoveis.api.model.imovel.ImovelRepository;
import com.centraldeimoveis.api.model.locacao.DadosAtualizaLocacao;
import com.centraldeimoveis.api.model.locacao.DadosCadastroLocacao;
import com.centraldeimoveis.api.model.locacao.DadosListagemLocacao;
import com.centraldeimoveis.api.model.locacao.Locacao;
import com.centraldeimoveis.api.model.locacao.LocacaoRepository;
import com.centraldeimoveis.api.model.pessoa.PessoaRepository;

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
    public void cadastrar(@RequestBody DadosCadastroLocacao dados) {
        var imovel = imovelRepository.getReferenceById(dados.imovel());
        var pessoa = pessoaRepository.getReferenceById(dados.pessoa());
        var locacao = new Locacao(dados, imovel, pessoa);
        locacaoRepository.save(locacao);
    }

    @GetMapping
    public Page<DadosListagemLocacao> listarPorPagina(Pageable paginacao) {
        return locacaoRepository.findAll(paginacao).map(DadosListagemLocacao::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaLocacao dados) {
        var locacao = locacaoRepository.getReferenceById(dados.id());
        locacao.atualizarLocacao(dados);
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
    //     var locacao = locacaoRepository.getReferenceById(id);
    //     locacao.exclusaoLogica();
    // }

}
