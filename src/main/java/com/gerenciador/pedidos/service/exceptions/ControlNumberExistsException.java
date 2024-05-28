package com.gerenciador.pedidos.service.exceptions;

public class ControlNumberExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ControlNumberExistsException(String msg) {
        super(msg);
    }

    public ControlNumberExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
