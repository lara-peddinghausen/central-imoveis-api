package com.centraldeimoveis.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraldeimoveis.api.model.locacao.Locacao;
import com.centraldeimoveis.api.model.locacao.Status;

public interface LocacaoRepository extends JpaRepository<Locacao, Integer>{
    // Busca a locacao pelo ID do imóvel associado e que ainda esteja ATIVA
    Optional<Locacao> findByImovelIdAndStatus(Integer imovelId, Status status);
}
