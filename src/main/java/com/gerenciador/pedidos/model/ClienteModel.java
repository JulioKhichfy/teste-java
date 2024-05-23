package com.gerenciador.pedidos.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table( name = "cliente" )
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer codigo;

    @OneToMany(mappedBy = "cliente" , fetch = FetchType.LAZY)
    private List<PedidoModel> pedidos;

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

    public List<PedidoModel> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoModel> pedidos) {
        this.pedidos = pedidos;
    }
}
