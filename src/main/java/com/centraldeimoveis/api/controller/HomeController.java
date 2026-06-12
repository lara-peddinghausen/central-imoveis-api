package com.centraldeimoveis.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

  // GET /api/home → só acessível com token válido
  @GetMapping("/home")
  public ResponseEntity<?> home(Authentication auth) {
    return ResponseEntity.ok(
        Map.of(
            "mensagem", "Bem-vindo!",
            "email", auth.getName()
        )
    );
  }
}