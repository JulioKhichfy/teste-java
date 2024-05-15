package com.gerenciador.pedidos.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_pedidos")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    public Pedido() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //número controle - número aleatório informado pelo cliente.
    @Column(name="numero_controle", unique=true, nullable=false)
    private Long numeroControle;

    //data cadastro (opcional)
    @Column(name="data_cadastro")
    private LocalDate dataCadastro;

    //nome - nome do produto
    @Column(name="nome_produto", nullable=false)
    private String nomeProduto;

    //valor - valor monetário unitário produto
    @Column(name="valor", nullable=false)
    private BigDecimal valor;

    //quantidade (opcional) - quantidade de produtos.
    @Column(name="quantidade")
    private Integer quantidade;

    //codigo cliente - identificação numérica do cliente
    @Column(name="codigo_cliente", unique=true, nullable=false)
    private Long codigoCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(Long numeroControle) {
        this.numeroControle = numeroControle;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return getId().equals(pedido.getId()) && getNumeroControle().equals(pedido.getNumeroControle()) && Objects.equals(getDataCadastro(), pedido.getDataCadastro()) && getNomeProduto().equals(pedido.getNomeProduto()) && getValor().equals(pedido.getValor()) && Objects.equals(getQuantidade(), pedido.getQuantidade()) && getCodigoCliente().equals(pedido.getCodigoCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumeroControle(), getDataCadastro(), getNomeProduto(), getValor(), getQuantidade(), getCodigoCliente());
    }
}
