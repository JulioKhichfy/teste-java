package com.gerenciador.pedidos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name = "cliente" )
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer codigo;

    @OneToMany(mappedBy = "cliente" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderModel> pedidos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<OrderModel> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<OrderModel> pedidos) {
        this.pedidos = pedidos;
    }
}
