package com.centraldeimoveis.api.dto.imovel;

import com.centraldeimoveis.api.model.administrador.Administrador;
import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.imovel.Status;
import com.centraldeimoveis.api.model.imovel.TipoLocacao;
import com.centraldeimoveis.api.model.proprietario.Proprietario;

public record DadosListagemImovel(
    Integer id,
    String nome,
    String rua,
    String cep,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String fotoUrl,
    TipoLocacao tipoLocacao,
    Status status,
    Administrador administrador,
    Proprietario proprietario
) {
    public DadosListagemImovel(Imovel imovel) {
        this(imovel.getId(), imovel.getNome(), imovel.getRua(), imovel.getCep(), imovel.getNumero(), imovel.getComplemento(), imovel.getBairro(),imovel.getCidade(), imovel.getEstado() , imovel.getFotoUrl(),imovel.getTipoLocacao(), imovel.getStatus(), imovel.getAdministrador(), imovel.getProprietario());
    }
}
