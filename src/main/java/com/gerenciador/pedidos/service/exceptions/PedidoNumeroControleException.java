package com.gerenciador.pedidos.service.exceptions;

public class PedidoNumeroControleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNumeroControleException(String msg) {
        super(msg);
    }

    public PedidoNumeroControleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
