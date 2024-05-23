package com.gerenciador.pedidos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table( name = "pedido" )
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @OneToOne(mappedBy = "pedido")
    private ItemPedidoModel item;

    @Column(name = "numero_controle")
    private Integer numeroControle;

    public ItemPedidoModel getItem() {
        return item;
    }

    public void setItem(ItemPedidoModel item) {
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(Integer numeroControle) {
        this.numeroControle = numeroControle;
    }
}
