package com.centraldeimoveis.api.model.administrador;

import java.time.LocalDate;
// import java.util.List;
// import com.centraldeimoveis.api.model.imovel.Imovel;
import jakarta.persistence.*;
import lombok.*;

/**
 * 
 * 
 * @author Lara Peddinghausen
 * @date 31/03/2026
 * 
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

    public Administrador(DadosCadastroAdministrador dados) {
        this.senha = dados.senha();
        this.email = dados.email();
        this.nome =dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();      
    }

    public void atualizarAdmnistrador(DadosAtualizacaoAdministrador dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.senha() != null){
            this.senha = dados.senha();
        }
        if(dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
    }
}
