package com.gerenciador.pedidos.service.exceptions;

public class IOPedidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IOPedidoException(String msg) {
        super(msg);
    }

    public IOPedidoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
