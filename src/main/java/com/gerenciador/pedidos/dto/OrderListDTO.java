package com.gerenciador.pedidos.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pedidos")
public class OrderListDTO {

    private List<OrderDTO> pedidos;

    public OrderListDTO() {
        // Construtor padrão necessário para a desserialização
    }

    @XmlElement(name = "pedido")
    public List<OrderDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<OrderDTO> pedidos) {
        this.pedidos = pedidos;
    }
}
