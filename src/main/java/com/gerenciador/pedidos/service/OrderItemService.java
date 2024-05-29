package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.repository.ClientRepository;
import com.gerenciador.pedidos.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderItemService {

    private Logger logger = Logger.getLogger(OrderItemService.class.getName());

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItemModel create(OrderItemModel itemPedido) {
        logger.info("Executando o método create itemPedido");
        return orderItemRepository.save(itemPedido);
    }


}
