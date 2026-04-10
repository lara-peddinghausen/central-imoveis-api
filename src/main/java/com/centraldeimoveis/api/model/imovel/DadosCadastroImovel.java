package com.centraldeimoveis.api.model.imovel;

// import com.centraldeimoveis.api.model.administrador.Administrador;
//import com.centraldeimoveis.api.model.proprietario.Proprietario;

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
    Integer administrador

    // @NotNull
    // Proprietario idProprietario

) {
    
}
