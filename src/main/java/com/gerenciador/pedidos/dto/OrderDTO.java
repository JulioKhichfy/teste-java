package com.gerenciador.pedidos.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "pedido")
public class OrderDTO {

    private int numeroControle;
    private String dataCadastro;
    private int codigoCliente;
    private List<ItemDTO> items;

    public OrderDTO() {
        // Construtor padrão necessário para a desserialização
    }

    @XmlElement(name="numeroControle")
    public int getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(int numeroControle) {
        this.numeroControle = numeroControle;
    }

    @XmlElement(name="dataCadastro")
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @XmlElement(name="codigoCliente")
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
