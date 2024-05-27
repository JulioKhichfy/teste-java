package com.gerenciador.pedidos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table( name = "item_pedido" )
public class ItemPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private ProdutoModel pedido;

    @Column
    private Integer quantidade;

    @Column
    private String nome;

    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "numero_controle")
    private Integer numeroControle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProdutoModel getPedido() {
        return pedido;
    }

    public void setPedido(ProdutoModel pedido) {
        this.pedido = pedido;
    }

    public Integer getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(Integer numeroControle) {
        this.numeroControle = numeroControle;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
