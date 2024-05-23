package com.gerenciador.pedidos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table( name = "produto" )
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String descricao;

    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal preco;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
