package com.gerenciador.pedidos.service.exceptions;

public class OrderDateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderDateException(String msg) {
        super(msg);
    }

    public OrderDateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
