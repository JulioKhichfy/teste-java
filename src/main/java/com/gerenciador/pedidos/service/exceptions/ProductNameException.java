package com.gerenciador.pedidos.service.exceptions;

public class ProductNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNameException(String msg) {
        super(msg);
    }

    public ProductNameException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
