package com.centraldeimoveis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.pessoa.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
}
