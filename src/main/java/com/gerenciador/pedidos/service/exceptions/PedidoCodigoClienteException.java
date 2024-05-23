package com.gerenciador.pedidos.service.exceptions;

public class PedidoCodigoClienteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoCodigoClienteException(String msg) {
        super(msg);
    }

    public PedidoCodigoClienteException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
