package com.gerenciador.pedidos.service;
import com.gerenciador.pedidos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderService {

    private Logger logger = Logger.getLogger(OrderService.class.getName());

    private final OrderRepository OrderRepository;

    @Autowired
    public OrderService(OrderRepository OrderRepository) {
        this.OrderRepository = OrderRepository;
    }

}
