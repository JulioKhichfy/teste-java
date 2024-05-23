package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.ClienteModel;
import com.gerenciador.pedidos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ClienteService {

    private Logger logger = Logger.getLogger(ClienteService.class.getName());

    @Autowired
    private ClienteRepository repository;

    public ClienteModel findByCodigo(Integer codigo){
        return repository.findByCodigo(codigo);
    }

    public ClienteModel findClienteFetchPedidos(Integer codigo){
        return repository.findClienteFetchPedidos(codigo);
    }

    public ClienteModel create(ClienteModel cliente) {
        logger.info("Executando o método create cliente");
        return repository.save(cliente);
    }
}
