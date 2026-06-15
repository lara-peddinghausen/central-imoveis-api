package com.centraldeimoveis.api.dto.imovel;

import com.centraldeimoveis.api.model.imovel.Status;

public record DadosAtualizaImovel(
    Integer id,
    String nome,
    Status status, 
    Integer proprietario,
    String fotoUrl
) {
    
}
