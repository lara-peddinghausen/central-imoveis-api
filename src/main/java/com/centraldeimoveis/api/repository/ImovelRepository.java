package com.centraldeimoveis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.imovel.Imovel;

public interface ImovelRepository extends JpaRepository<Imovel, Integer>{
    
}
