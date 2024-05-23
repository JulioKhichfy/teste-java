package com.gerenciador.pedidos.resources.exceptions;

import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(IOPedidoException.class)
    public ResponseEntity<StandardError> IOPedidoException(IOPedidoException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(PedidoException.class)
    public ResponseEntity<StandardError> pedidoException(PedidoException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(PedidoNullableException.class)
    public ResponseEntity<StandardError> pedidoNullableException(PedidoNullableException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoQuantidadeException.class)
    public ResponseEntity<StandardError> pedidoQuantidadeException(PedidoQuantidadeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoNumeroControleException.class)
    public ResponseEntity<StandardError> pedidoQuantidadeException(PedidoNumeroControleException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoNomeProdutoControleException.class)
    public ResponseEntity<StandardError> pedidoNomeProdutoControleException(PedidoNomeProdutoControleException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoNumeroControleExistsException.class)
    public ResponseEntity<StandardError> pedidoNumeroControleExistsException(PedidoNumeroControleExistsException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoCodigoClienteException.class)
    public ResponseEntity<StandardError> pedidoCodigoClienteException(PedidoCodigoClienteException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }



}
