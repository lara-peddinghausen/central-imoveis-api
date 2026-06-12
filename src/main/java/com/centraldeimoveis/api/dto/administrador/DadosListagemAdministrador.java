package com.centraldeimoveis.api.dto.administrador;

import java.time.LocalDate;

import com.centraldeimoveis.api.model.administrador.Administrador;

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
