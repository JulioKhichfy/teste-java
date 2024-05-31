package com.gerenciador.pedidos.service.exceptions;

public class ControlNumberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ControlNumberNotFoundException(String msg) {
        super(msg);
    }

    public ControlNumberNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
