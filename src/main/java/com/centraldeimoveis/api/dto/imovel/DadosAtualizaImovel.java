package com.centraldeimoveis.api.dto.imovel;

import com.centraldeimoveis.api.model.imovel.Status;
import com.centraldeimoveis.api.model.imovel.TipoLocacao;

public record DadosAtualizaImovel(
    Integer id,
    String nome,
    String numero,
    String complemento,
    TipoLocacao tipoLocacao,
    Status status, 
    Integer proprietario,
    String fotoUrl
) {
    
}
