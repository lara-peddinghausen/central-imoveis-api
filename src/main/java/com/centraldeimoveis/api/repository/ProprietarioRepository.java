package com.centraldeimoveis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.proprietario.Proprietario;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Integer> {
    
}
