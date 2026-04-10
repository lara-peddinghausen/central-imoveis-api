package com.centraldeimoveis.api.model.administrador;

import java.time.LocalDate;

public record DadosListagemAdministrador(
    Integer id,
    String email,
    String nome,
    LocalDate dataNascimento,
    String cpf
) {
    public DadosListagemAdministrador(Administrador administrador) {
        this(administrador.getId(), administrador.getEmail(), administrador.getNome(), administrador.getDataNascimento(), administrador.getCpf());
    }
}
