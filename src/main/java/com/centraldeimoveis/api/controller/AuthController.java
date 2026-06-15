package com.centraldeimoveis.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraldeimoveis.api.dto.auth.AuthResponse;
import com.centraldeimoveis.api.dto.auth.LoginRequest;
import com.centraldeimoveis.api.repository.AdministradorRepository;
import com.centraldeimoveis.api.security.JwtUtil;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            // 1. O Spring Security valida o e-mail e a senha encriptada automaticamente
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.senha()));

            // Busca o administrador do banco para termos acesso aos dados dele (nome,
            // e-mail, etc.)
            var administrador = administradorRepository.findByEmail(req.email())
                    .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

            // 2. Gera o token JWT com o e-mail do Administrador
            String token = jwtUtil.generateToken(req.email());

            // 🚀 3. RETORNO ATUALIZADO: Enviando todos os dados para o React Native salvar
            // e usar no Perfil!
            return ResponseEntity.ok(new AuthResponse(
                    token,
                    administrador.getEmail(),
                    "ROLE_ADMIN",
                    administrador.getId(),
                    administrador.getNome(), // ◄ NOVO
                    administrador.getCpf(), // ◄ NOVO
                    administrador.getDataNascimento() != null ? administrador.getDataNascimento().toString() : null // ◄                                                                                                   // nulo)
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "E-mail ou senha inválidos"));
        }
    }
}