package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.ItemPedidoModel;
import com.gerenciador.pedidos.repository.ItemsPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ItemPedidoService {

    private Logger logger = Logger.getLogger(ItemPedidoService.class.getName());

    @Autowired
    private ItemsPedidoRepository repository;

    public ItemPedidoModel create(ItemPedidoModel itemPedido) {
        logger.info("Executando o método create itemPedido");
        return repository.save(itemPedido);
    }
}
