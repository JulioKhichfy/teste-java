package com.gerenciador.pedidos.service;
import com.gerenciador.pedidos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    private Logger logger = Logger.getLogger(OrderService.class.getName());

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean existsByNumeroControle(Integer numeroControle){
        return orderRepository.existsByNumeroControle(numeroControle);
    }
    public long countByNumeroControleIn(List<Integer> numerosControle){
        return orderRepository.countByNumeroControleIn(numerosControle);
    }

}
