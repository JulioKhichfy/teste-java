package com.gerenciador.pedidos.service.exceptions;

public class OrderSizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderSizeException(String msg) {
        super(msg);
    }

    public OrderSizeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
