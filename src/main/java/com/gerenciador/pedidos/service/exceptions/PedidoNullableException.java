package com.gerenciador.pedidos.service.exceptions;

public class PedidoNullableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNullableException(String msg) {
        super(msg);
    }

    public PedidoNullableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
