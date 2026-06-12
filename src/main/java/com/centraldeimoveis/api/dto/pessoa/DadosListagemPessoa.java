package com.centraldeimoveis.api.dto.pessoa;

import java.time.LocalDate;

import com.centraldeimoveis.api.model.pessoa.Pessoa;

public record DadosListagemPessoa(
    Integer id,
    String email,
    String nome,
    LocalDate dataNascimento,
    String cpf,
    String telefone) 
    {
        public DadosListagemPessoa(Pessoa pessoa) {
        this(pessoa.getId(), pessoa.getEmail(), pessoa.getNome(), pessoa.getDataNascimento(), pessoa.getCpf(), pessoa.getTelefone());
    }
}
