package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PedidoService {

    private Logger logger = Logger.getLogger(PedidoService.class.getName());

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClienteService clienteService;
}
