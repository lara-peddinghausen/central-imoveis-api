package com.centraldeimoveis.api.model.imovel;

import com.centraldeimoveis.api.model.administrador.Administrador;

import jakarta.persistence.*;
import lombok.*;

/**
 * 
 * 
 * @author Lara Peddinghausen
 * @date 07/03/2026
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "imovel")
public class Imovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String rua;
    private String cep;
    private String numero;
    private String complemento;

    @Enumerated(EnumType.STRING)
    private TipoLocacao tipoLocacao;

    @Enumerated(EnumType.STRING)
    private Status status;

  
    @ManyToOne
    @JoinColumn(name = "id_administrador") //FK
    private Administrador administrador;

    // @Embedded
    // @ManyToOne
    // @JoinColumn(name = "idProprietario")
    // private Proprietario idProprietario;

    public Imovel(DadosCadastroImovel dados, Administrador administrador) {
        this.nome = dados.nome();
        this.rua = dados.rua();
        this.cep = dados.cep();
        this.numero = dados.cep();
        this.complemento = dados.complemento();
        this.tipoLocacao = dados.tipoLocacao();
        this.status = dados.status();
        this.administrador = administrador;
        // this.idProprietario = dados.idProprietario();
    }

    
}
