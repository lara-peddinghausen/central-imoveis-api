package com.centraldeimoveis.api.model.proprietario;

import java.time.LocalDate;

import com.centraldeimoveis.api.dto.proprietario.DadosAtualizaProprietario;
import com.centraldeimoveis.api.dto.proprietario.DadosCadastroProprietario;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um proprietário do sistema de central de imóveis.
 * 
 * Contém informações pessoais como nome, e-mail, CPF, telefone e data de nascimento.
 * 
 * @author Lara Peddinghausen
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "proprietario")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String nome;
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    private String telefone;

    /**
     * Cria um proprietário a partir dos dados de cadastro.
     *
     * @param dados objeto contendo as informações necessárias para criação do proprietário
     */
    public Proprietario(DadosCadastroProprietario dados) {
        this.email = dados.email();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
    }

    /**
     * Atualiza os dados do proprietário com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarProprietario(DadosAtualizaProprietario dados) {
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
