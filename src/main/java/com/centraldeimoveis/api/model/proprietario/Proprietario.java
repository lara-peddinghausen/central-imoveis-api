package com.centraldeimoveis.api.model.proprietario;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

/**
 * 
 * @author Lara Peddinghausen
 * @date 14/04/2026
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

    private String email;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;

    public Proprietario(DadosCadastroProprietario dados) {
        this.email = dados.email();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
    }

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
