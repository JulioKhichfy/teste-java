package com.gerenciador.pedidos.service.exceptions;

public class PedidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoException(String msg) {
        super(msg);
    }

    public PedidoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
