package com.centraldeimoveis.api.model.pessoa;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma pessoa no sistema de central de imóveis.
 * Pode atuar como locatário de um imóvel, seja para locação residencial ou por temporada.
 * Contém informações pessoais como nome, e-mail, CPF, telefone e data de nascimento.
 *
 * @author Lara Peddinghausen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;

    /**
     * Cria uma pessoa a partir dos dados de cadastro.
     *
     * @param dados objeto contendo as informações necessárias para criação da pessoa
     */
    public Pessoa(DadosCadastroPessoa dados) {
        this.email = dados.email();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
    }

    /**
     * Atualiza os dados da pessoa com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarPessoa(DadosAtualizaPessoa dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
    }
    
}
