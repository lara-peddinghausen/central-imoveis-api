package com.centraldeimoveis.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.administrador.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{
    // JpaRepository já fornece:
  //   save(), findById(), findAll(), deleteById(), count(), existsById()...

  // Spring Data lê o nome do método e gera automaticamente:
  // SELECT * FROM usuarios WHERE email = ?
  Optional<Administrador> findByEmail(String email);

  // Verifica se já existe um usuário com aquele email (sem trazer o objeto)
  // Útil para validação no cadastro sem carregar o usuário completo
  boolean existsByEmail(String email);
}
