package com.gerenciador.pedidos.service.exceptions;

public class DateNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DateNotFoundException(String msg) {
        super(msg);
    }

    public DateNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}