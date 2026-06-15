package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // ◄ IMPORTANTE IMPORTAR

import com.centraldeimoveis.api.dto.imovel.DadosAtualizaImovel;
import com.centraldeimoveis.api.dto.imovel.DadosCadastroImovel;
import com.centraldeimoveis.api.dto.imovel.DadosListagemImovel;
import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.proprietario.Proprietario;
import com.centraldeimoveis.api.repository.AdministradorRepository;
import com.centraldeimoveis.api.repository.ImovelRepository;
import com.centraldeimoveis.api.repository.ProprietarioRepository;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> cadastrar(
            @ModelAttribute @jakarta.validation.Valid DadosCadastroImovel dados,
            @RequestParam(value = "foto", required = false) MultipartFile arquivoFoto) {

        var administrador = administradorRepository.getReferenceById(dados.administrador());

        Proprietario proprietario = null;
        if (dados.proprietario() != null) {
            proprietario = proprietarioRepository.getReferenceById(dados.proprietario());
        }

        var imovel = new Imovel(dados, administrador, proprietario);

        if (arquivoFoto != null && !arquivoFoto.isEmpty()) {
            try {

                String diretorioDestino = "C:/Users/LaraP/OneDrive/Desktop/Central de imóveis - Backend/uploads/";
                File pasta = new File(diretorioDestino);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                String extensao = arquivoFoto.getOriginalFilename()
                        .substring(arquivoFoto.getOriginalFilename().lastIndexOf("."));
                String nomeDoArquivo = java.util.UUID.randomUUID().toString() + extensao;
                File destino = new File(diretorioDestino + nomeDoArquivo);
                arquivoFoto.transferTo(destino);

                String urlDaImagem = "/uploads/" + nomeDoArquivo;
                imovel.setFotoUrl(urlDaImagem);

            } catch (IOException e) {
                return ResponseEntity.status(500).body("Erro ao salvar o arquivo de imagem.");
            }
        }

        // 🚀 SALVA ANTES DE RETORNAR: Assim o banco gera o ID automático (Identity)
        var imovelSalvo = imovelRepository.save(imovel);

        // 🚀 RETORNA O OBJETO COMPLETO: Agora o front-end terá acesso a
        // resposta.data.id
        return ResponseEntity.status(201).body(imovelSalvo);
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<DadosListagemImovel>> listar(
            @org.springframework.data.web.PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao,
            @RequestParam(required = false) Long administradorId) {
        Page<Imovel> page;

        // Se o React Native passar o id na URL (?administradorId=2), filtra. Caso
        // contrário, traz todos.
        if (administradorId != null) {
            page = imovelRepository.findByAdministradorId(administradorId, paginacao);
        } else {
            page = imovelRepository.findAll(paginacao);
        }

        var dtoPage = page.map(DadosListagemImovel::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Integer id) {
        if (!imovelRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("mensagem", "Imóvel não encontrado."));
        }
        var imovel = imovelRepository.getReferenceById(id);
        return ResponseEntity.ok(imovel);
    }

    @PostMapping("/vincular-proprietario")
    @Transactional
    public ResponseEntity<String> vincularProprietario(@RequestBody DadosAtualizaImovel dados) {
        var imovel = imovelRepository.getReferenceById(dados.id());

        Proprietario proprietario = null;
        if (dados.proprietario() != null) {
            proprietario = proprietarioRepository.getReferenceById(dados.proprietario());
        }

        imovel.atualizarImovel(dados, proprietario);
        return ResponseEntity.ok("Proprietário vinculado com sucesso!");
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizaImovel dados) {
        var imovel = imovelRepository.getReferenceById(dados.id());

        // Busca o proprietário diretamente do repository se o ID foi enviado
        Proprietario proprietario = null;
        if (dados.proprietario() != null) {
            // Removido o .getRepository() daqui:
            proprietario = proprietarioRepository.getReferenceById(dados.proprietario());
        }

        // Repassa os dados mapeados para o Model se encarregar de atualizar
        imovel.atualizarImovel(dados, proprietario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        imovelRepository.deleteById(id);
    }

    // Exclusão lógica
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    // var imovel = imovelRepository.getReferenceById(id);
    // imovel.exclusaoLogica();
    // }

}
