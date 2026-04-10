package com.centraldeimoveis.api.model.imovel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImovelRepository extends JpaRepository<Imovel, Integer>{
    
}
