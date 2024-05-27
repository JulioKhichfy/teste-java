package com.gerenciador.pedidos.service.exceptions;

public class ItemPedidoNumeroControleExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ItemPedidoNumeroControleExistsException(String msg) {
        super(msg);
    }

    public ItemPedidoNumeroControleExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
