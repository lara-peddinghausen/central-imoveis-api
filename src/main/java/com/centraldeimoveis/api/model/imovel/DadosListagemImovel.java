package com.centraldeimoveis.api.model.imovel;

import com.centraldeimoveis.api.model.administrador.Administrador;

public record DadosListagemImovel(
    Integer id,
    String nome,
    String rua,
    String cep,
    String numero,
    String complemento,
    TipoLocacao tipoLocacao,
    Status status,
    Administrador administrador
) {
    public DadosListagemImovel(Imovel imovel) {
        this(imovel.getId(), imovel.getNome(), imovel.getRua(), imovel.getCep(), imovel.getNumero(), imovel.getComplemento(), imovel.getTipoLocacao(), imovel.getStatus(), imovel.getAdministrador());
    }
}
