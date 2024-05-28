package com.gerenciador.pedidos.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement(name = "pedido")
public class OrderDTO {

    private int numeroControle;
    private String dataCadastro;
    private String nome;
    private double valor;
    private int quantidade;
    private int codigoCliente;

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

    @XmlElement(name="nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlElement(name="valor")
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @XmlElement(name="quantidade")
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @XmlElement(name="codigoCliente")
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
}
