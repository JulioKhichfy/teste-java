package com.gerenciador.pedidos.service.exceptions;

public class PedidoNumeroControleExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNumeroControleExistsException(String msg) {
        super(msg);
    }

    public PedidoNumeroControleExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
