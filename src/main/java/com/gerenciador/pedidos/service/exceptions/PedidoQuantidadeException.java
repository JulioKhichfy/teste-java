package com.gerenciador.pedidos.service.exceptions;

public class PedidoQuantidadeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoQuantidadeException(String msg) {
        super(msg);
    }

    public PedidoQuantidadeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
