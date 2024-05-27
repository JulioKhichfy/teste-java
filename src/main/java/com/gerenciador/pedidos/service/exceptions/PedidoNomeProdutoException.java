package com.gerenciador.pedidos.service.exceptions;

public class PedidoNomeProdutoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNomeProdutoException(String msg) {
        super(msg);
    }

    public PedidoNomeProdutoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
