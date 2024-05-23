package com.gerenciador.pedidos.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pedidos")
public class PedidoListDTO {

    private List<PedidoDTO> pedidos;

    public PedidoListDTO() {
        // Construtor padrão necessário para a desserialização
    }

    @XmlElement(name = "pedido")
    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }
}
