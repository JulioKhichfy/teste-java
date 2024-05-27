package com.gerenciador.pedidos.service.exceptions;

public class FileExtensionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileExtensionException(String msg) {
        super(msg);
    }

    public FileExtensionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

