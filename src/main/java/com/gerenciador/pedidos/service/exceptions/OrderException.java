package com.gerenciador.pedidos.service.exceptions;

public class OrderException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderException(String msg) {
        super(msg);
    }

    public OrderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
