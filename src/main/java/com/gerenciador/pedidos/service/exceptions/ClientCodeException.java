package com.gerenciador.pedidos.service.exceptions;

public class ClientCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClientCodeException(String msg) {
        super(msg);
    }

    public ClientCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
