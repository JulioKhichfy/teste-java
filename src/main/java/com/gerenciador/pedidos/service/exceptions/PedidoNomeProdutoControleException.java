package com.gerenciador.pedidos.service.exceptions;

public class PedidoNomeProdutoControleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNomeProdutoControleException(String msg) {
        super(msg);
    }

    public PedidoNomeProdutoControleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
