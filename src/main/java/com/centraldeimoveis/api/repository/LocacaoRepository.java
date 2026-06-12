package com.centraldeimoveis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.locacao.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Integer>{
    
}
