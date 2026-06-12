package com.centraldeimoveis.api.dto.imovel;

import com.centraldeimoveis.api.model.imovel.Status;
import com.centraldeimoveis.api.model.imovel.TipoLocacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroImovel(

    @NotBlank
    String nome,

    @NotBlank
    String rua,

    String cep,

    @NotBlank
    String numero,

    String complemento,

    @NotNull
    TipoLocacao tipoLocacao,

    @NotNull
    Status status,

    @NotNull
    Integer administrador,

    @NotNull
    Integer proprietario

) {
    
}
