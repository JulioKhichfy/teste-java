package com.gerenciador.pedidos.service.exceptions;

public class PedidoDateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoDateException(String msg) {
        super(msg);
    }

    public PedidoDateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
