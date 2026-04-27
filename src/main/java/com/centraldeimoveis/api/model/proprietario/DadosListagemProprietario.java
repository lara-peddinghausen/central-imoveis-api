package com.centraldeimoveis.api.model.proprietario;

import java.time.LocalDate;


public record DadosListagemProprietario(
    Integer id,
    String email,
    String nome,
    LocalDate dataNascimento,
    String cpf,
    String telefone
) {
    public DadosListagemProprietario(Proprietario proprietario) {
        this(proprietario.getId(), proprietario.getEmail(), proprietario.getNome(), proprietario.getDataNascimento(), proprietario.getCpf(), proprietario.getTelefone());
    }
} 

