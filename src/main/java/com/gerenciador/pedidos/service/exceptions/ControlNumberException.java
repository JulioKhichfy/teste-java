package com.gerenciador.pedidos.service.exceptions;

public class ControlNumberException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ControlNumberException(String msg) {
        super(msg);
    }

    public ControlNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
