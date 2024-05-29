package com.gerenciador.pedidos.service;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.repository.OrderRepository;
import com.gerenciador.pedidos.service.exceptions.ControlNumberExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public void checkNumeroControleInDataBase(List<ClientModel> clients) {

        List<Integer> numerosControle = new ArrayList<>();

        for(ClientModel clientModel : clients) {
            List<OrderModel> orders = clientModel.getPedidos();
            List<Integer> numerosControleByClient = clientModel.getPedidos().stream()
                    .map(OrderModel::getNumeroControle)
                    .collect(Collectors.toList());
            numerosControle.addAll(numerosControleByClient);
        }

        //Não poderá aceitar um número de controle já cadastrado.
        long count = orderRepository.countByNumeroControleIn(numerosControle);
        if(count > 0){
            throw new ControlNumberExistsException("Erro: Número de controle já está cadastrado");
        }

    }

}
