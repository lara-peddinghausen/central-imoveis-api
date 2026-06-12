package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // 🚀 ADICIONADO
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.centraldeimoveis.api.dto.administrador.DadosAtualizaAdministrador;
import com.centraldeimoveis.api.dto.administrador.DadosCadastroAdministrador;
import com.centraldeimoveis.api.dto.administrador.DadosListagemAdministrador;
import com.centraldeimoveis.api.model.administrador.Administrador;
import com.centraldeimoveis.api.repository.AdministradorRepository;

import jakarta.transaction.Transactional;
import java.util.Map;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@RequestBody DadosCadastroAdministrador dados, UriComponentsBuilder uriBuilder) {
        var administrador = new Administrador(dados);
        
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        administrador.setSenha(senhaCriptografada);
        
        administradorRepository.save(administrador);
        
        var uri = uriBuilder.path("/administrador/{id}").buildAndExpand(administrador.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // ── 🚀 NOVA ROTA: GET /administrador/perfil ────────────────────────────
    // Retorna as informações do Administrador que está logado atualmente no celular.
    // O Spring Security lê o Token JWT enviado no Header e injeta o objeto "auth" automaticamente.
    @GetMapping("/perfil")
    public ResponseEntity<?> obterPerfilLogado(Authentication auth) {
        var administrador = administradorRepository
            .findByEmail(auth.getName()) // auth.getName() extrai o e-mail de dentro do Token JWT!
            .orElseThrow(() -> new RuntimeException("Administrador logado não encontrado"));

        // Retorna um JSON seguro com os dados do perfil (Nome, E-mail, CPF) e SEM A SENHA!
        return ResponseEntity.ok(Map.of(
            "id", administrador.getId(),
            "nome", administrador.getNome(),
            "email", administrador.getEmail(),
            "cpf", administrador.getCpf() != null ? administrador.getCpf() : ""
        ));
    }

    @GetMapping
    public Page<DadosListagemAdministrador> listarPorPagina(Pageable paginacao) {
        return administradorRepository.findAll(paginacao).map(DadosListagemAdministrador::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody DadosAtualizaAdministrador dados) {
        var administrador = administradorRepository.getReferenceById(dados.id());
        administrador.atualizarAdministrador(dados);
        return ResponseEntity.ok().build(); // Adicionado retorno HTTP 200 limpo
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        administradorRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Adicionado retorno HTTP 204 padrão REST
    }
}