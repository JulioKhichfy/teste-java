package com.gerenciador.pedidos.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pedidos")
public class PedidosArquivo {

    private List<PedidoArquivo> pedidos;

    public PedidosArquivo() {
        // Construtor padrão necessário para a desserialização
    }

    @XmlElement(name = "pedido")
    public List<PedidoArquivo> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoArquivo> pedidos) {
        this.pedidos = pedidos;
    }
}
