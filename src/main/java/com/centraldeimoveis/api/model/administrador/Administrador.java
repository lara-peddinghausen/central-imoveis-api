package com.centraldeimoveis.api.model.administrador;

import java.time.LocalDate;
// import java.util.List;
// import com.centraldeimoveis.api.model.imovel.Imovel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um administrador do sistema de central de imóveis.
 * 
 * Contém informações pessoais e credenciais de acesso, como nome, e-mail, senha, CPF e data de nascimento.
 * 
 * @author Lara Peddinghausen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String senha;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;

    // @OneToMany(mappedBy = "administrador")
    // private List<Imovel> imoveis;

    /**
     * Cria um administrador a partir dos dados de cadastro.
     *
     * @param dados objeto contendo as informações necessárias para criação do administrador
     */
    public Administrador(DadosCadastroAdministrador dados) {
        this.senha = dados.senha();
        this.email = dados.email();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
    }

    /**
     * Atualiza os dados do administrador com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarAdministrador(DadosAtualizacaAdministrador dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
        if (dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
    }
}
