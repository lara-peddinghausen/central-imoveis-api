package com.centraldeimoveis.api.service;

import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.locacao.Locacao;
import com.centraldeimoveis.api.repository.ImovelRepository;
import com.centraldeimoveis.api.repository.LocacaoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class LocacaoService {
    @Transactional
    public ContratoResponseDTO cadastrarContrato(ContratoRequestDTO dto) {
        // 1. Busca o imóvel que está sendo alugado
        Imovel imovel = ImovelRepository.findById(dto.imovelId())
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado"));

        // 🔒 VALIDAÇÃO DE SEGURANÇA: Impede alugar um imóvel que já está ocupado
        if (imovel.getStatus() == status.ALUGADO) {
            throw new BusinessException("Este imóvel já possui um contrato ativo!");
        }

        // 2. Cria e salva o contrato no banco de dados
        Locacao locacao = new Locacao(dto);
        LocacaoRepository.save(locacao);

        // 🚀 A AUTOMAÇÃO: Muda o status do imóvel para ALUGADO automaticamente
        imovel.setStatus(status.ALUGADO);
        ImovelRepository.save(imovel); // Atualiza o status no SQL Server

        return new ContratoResponseDTO(locacao);
    }
}
