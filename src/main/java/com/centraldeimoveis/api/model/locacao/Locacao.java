package com.centraldeimoveis.api.model.locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.centraldeimoveis.api.dto.locacao.DadosAtualizaLocacao;
import com.centraldeimoveis.api.dto.locacao.DadosCadastroLocacao;
import com.centraldeimoveis.api.model.imovel.Imovel;
import com.centraldeimoveis.api.model.pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma locação no sistema de central de imóveis.
 *
 * Contém informações como status, datas de início e término, aluguel,
 * observação, imóvel e pessoa vinculados.
 *
 * @author Lara Peddinghausen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "locacao")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVA;

    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private BigDecimal aluguel;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "IdImovel")
    private Imovel imovel;

    @ManyToOne(optional = true)
    @JoinColumn(name = "IdPessoa")
    private Pessoa pessoa;

    /**
     * Cria uma locação a partir dos dados de cadastro.
     *
     * @param dados  objeto contendo as informações necessárias para criação da
     *               locação
     * @param imovel o imóvel vinculado à locação
     * @param pessoa a pessoa vinculada à locação
     */
    public Locacao(DadosCadastroLocacao dados, Imovel imovel, Pessoa pessoa) {
        this.status = dados.status() != null ? dados.status() : Status.ATIVA;
        this.dataInicio = dados.dataInicio();
        this.dataTermino = dados.dataTermino();
        this.aluguel = dados.aluguel();
        this.observacao = dados.observacao();
        this.imovel = imovel;
        this.pessoa = pessoa;
    }

    /**
     * Atualiza os dados da locação com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarLocacao(DadosAtualizaLocacao dados, Pessoa novaPessoa) {
        if (dados.observacao() != null) {
            this.observacao = dados.observacao();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
        if (dados.pessoa() != null) {
            this.pessoa = novaPessoa;
        }
        if (dados.aluguel() != null) {
            this.aluguel = dados.aluguel();
        }
    }

}
