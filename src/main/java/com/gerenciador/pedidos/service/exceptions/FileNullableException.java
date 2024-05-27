package com.gerenciador.pedidos.service.exceptions;

public class FileNullableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileNullableException(String msg) {
        super(msg);
    }

    public FileNullableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
