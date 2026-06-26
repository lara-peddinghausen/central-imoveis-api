package com.centraldeimoveis.api.controller;

import java.util.List;

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
import com.centraldeimoveis.api.model.locacao.Status;
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

    @GetMapping("/historico/{imovelId}")
    public ResponseEntity<List<Locacao>> buscarHistoricoPorImovel(@PathVariable Integer imovelId) {
        List<Locacao> historico = locacaoRepository.findByImovelId(imovelId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/imovel/{imovelId}")
    public ResponseEntity<Locacao> buscarPorImovel(@PathVariable Integer imovelId) {
        // Altere aqui para a lógica do seu repositório que busca pelo ID do imóvel
        var locacaoOpt = locacaoRepository.findByImovelIdAndStatus(imovelId, Status.ATIVA);

        return locacaoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // SE tiver um método para buscar a locação pelo ID dela mesma: GET /locacao/7
    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscarPorId(@PathVariable Integer id) {
        // findById retorna um Optional seguro
        return locacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Locacao> atualizar(@RequestBody @jakarta.validation.Valid DadosAtualizaLocacao dados) {

        var locacao = locacaoRepository.findById(dados.id())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Locação não encontrada"));

        // Busca a pessoa encontrada pelo ID enviado
        Pessoa pessoa = null;
        if (dados.pessoa() != null) {
            pessoa = pessoaRepository.findById(dados.pessoa()).orElse(null);
        }

        // Modifica os atributos na entidade
        locacao.atualizarLocacao(dados, pessoa);

        // Retorna a entidade atualizada
        return ResponseEntity.ok(locacao);
    }

    @PutMapping("/{id}/cancelar")
    @Transactional
    public ResponseEntity<Void> cancelarLocacao(@PathVariable Integer id) {
        // Busca a locação
        var locacao = locacaoRepository.getReferenceById(id);

        // Modifica o status do contrato para CANCELADA
        locacao.setStatus(Status.CANCELADA); // Ou use seu método interno, ex: locacao.cancelar();

        // Busca o imóvel vinculado e altera o status para DISPONIVEL
        var imovel = locacao.getImovel();
        if (imovel != null) {
            imovel.setStatus(com.centraldeimoveis.api.model.imovel.Status.DISPONIVEL);
        }

        // Como está com @Transactional, o save é automático ao fim do método, mas para
        // garantir:
        locacaoRepository.save(locacao);

        return ResponseEntity.noContent().build();
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
