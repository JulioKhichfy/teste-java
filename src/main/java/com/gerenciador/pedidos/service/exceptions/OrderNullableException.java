package com.gerenciador.pedidos.service.exceptions;

public class OrderNullableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderNullableException(String msg) {
        super(msg);
    }

    public OrderNullableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
