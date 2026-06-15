package com.centraldeimoveis.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.imovel.Imovel;

public interface ImovelRepository extends JpaRepository<Imovel, Integer>{
    // Busca apenas os imóveis onde o administrador dono tem o ID informado
    Page<Imovel> findByAdministradorId(Long administradorId, Pageable pageable);
}
