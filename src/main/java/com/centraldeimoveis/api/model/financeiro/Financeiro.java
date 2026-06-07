package com.centraldeimoveis.api.model.financeiro;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.centraldeimoveis.api.model.imovel.Imovel;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um registro financeiro no sistema de central de imóveis.
 *
 * Contém informações como tipo de movimentação, valor, data, descrição e o imóvel vinculado.
 *
 * @author Lara Peddinghausen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "financeiro")
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFinanceiro")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private BigDecimal valor;

    private LocalDate data;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idImovel")
    private Imovel imovel;

    /**
     * Cria um registro financeiro a partir dos dados de cadastro.
     *
     * @param dados objeto contendo as informações necessárias para criação do registro financeiro
     * @param imovel o imóvel vinculado ao registro financeiro
     */
    public Financeiro(DadosCadastroFinanceiro dados, Imovel imovel) {
        this.tipoMovimentacao = dados.tipoMovimentacao();
        this.valor = dados.valor();
        this.data = dados.data();
        this.descricao = dados.descricao();
        this.imovel = imovel;
    }

    /**
     * Atualiza os dados do registro financeiro com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarFinanceiro(DadosAtualizaFinanceiro dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
    }

}
