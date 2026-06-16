package com.centraldeimoveis.api.model.imovel;

import com.centraldeimoveis.api.dto.imovel.DadosAtualizaImovel;
import com.centraldeimoveis.api.dto.imovel.DadosCadastroImovel;
import com.centraldeimoveis.api.model.administrador.Administrador;
import com.centraldeimoveis.api.model.proprietario.Proprietario;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um imóvel no sistema de central de imóveis.
 *
 * Contém informações como nome, endereço, tipo
 * de locação, status, proprietário e o administrador responsável.
 *
 * @author Lara Peddinghausen
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
    private String bairro;
    private String cidade;
    private String estado;
    private String fotoUrl;

    @Enumerated(EnumType.STRING)
    private TipoLocacao tipoLocacao;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DISPONIVEL;

    @ManyToOne
    @JoinColumn(name = "id_administrador")
    private Administrador administrador;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_proprietario", nullable = true)
    private Proprietario proprietario;

    /**
     * Cria um imóvel a partir dos dados de cadastro.
     *
     * @param dados         objeto contendo as informações necessárias para criação
     *                      do imóvel
     * @param administrador o administrador responsável pelo imóvel
     * @param proprietario  o proprietário do imóvel
     */
    public Imovel(DadosCadastroImovel dados, Administrador administrador, Proprietario proprietario) {
        this.nome = dados.nome();
        this.rua = dados.rua();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.complemento = dados.complemento();
        this.bairro = dados.bairro();
        this.cidade = dados.cidade();
        this.estado = dados.estado();
        this.fotoUrl = dados.fotoUrl();
        this.tipoLocacao = dados.tipoLocacao();
        this.administrador = administrador;
        this.proprietario = proprietario;

        if (dados.status() != null) {
            this.status = dados.status();
        }
    }

    /**
     * Atualiza os dados do imóvel com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarImovel(DadosAtualizaImovel dados, Proprietario novoProprietario) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.complemento() != null) {
            this.complemento = dados.complemento();
        }
        if (dados.tipoLocacao() != null) {
            this.tipoLocacao = dados.tipoLocacao();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
        if (dados.proprietario() != null) {
            this.proprietario = novoProprietario;
        }
        if (dados.fotoUrl() != null) {
            this.fotoUrl = dados.fotoUrl();
        }
    }

}
