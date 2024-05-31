package com.gerenciador.pedidos.service;
import com.gerenciador.pedidos.converter.Converter;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.repository.OrderRepository;
import com.gerenciador.pedidos.service.exceptions.ControlNumberExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO findByControNumber(Integer controlNumber) {
        logger.info("Obter pedido com número de controle: {}",  controlNumber);
        OrderModel order = orderRepository.findByNumeroControle(controlNumber);
        return Converter.convertOrderModelToOrderDTO(order);
    }

    public List<OrderDTO> findByDate(String dataPedido) {
        logger.info("Obter pedidos com data: {}", dataPedido);
        List<OrderModel> orders = orderRepository.findByDataPedido(LocalDate.parse(dataPedido));
        return Converter.buildListOrderDTO(orders);
    }

    public List<OrderDTO> findAll(){
        logger.info("Obter todos os pedidos");
        List<OrderModel> orders = orderRepository.findAll();

        // Ordena a lista pelo campo dataPedido em ordem decrescente
        List<OrderModel> sortedOrders = sortedOrdersByDate(orders);

        return Converter.buildListOrderDTO(sortedOrders);
    }

    public void checkNumeroControleInDataBase(List<ClientModel> clients) {
        logger.info("Verificar se número de controle já está cadastrado");
        List<Integer> controlNumbers = new ArrayList<>();

        for(ClientModel clientModel : clients) {
            List<Integer> controlNumbersByClient = clientModel.getPedidos().stream()
                    .map(OrderModel::getNumeroControle)
                    .collect(Collectors.toList());
            controlNumbers.addAll(controlNumbersByClient);
        }

        //Não poderá aceitar um número de controle já cadastrado.
        long count = orderRepository.countByControlNumbers(controlNumbers);
        if(count > 0) {
            //Importante mostrar qual o número de controle ja se encontra cadastrado
            int controlNumber = 0;
            for(Integer numControl: controlNumbers) {
                OrderModel order = orderRepository.findByNumeroControle(numControl);
                if(order != null) {
                    controlNumber = order.getNumeroControle();
                    break;
                }
            }
            logger.error("Erro: Número de controle {} já cadastrado ", controlNumber);
            throw new ControlNumberExistsException("Erro: Número de controle já cadastrado");
        }
    }

    private List<OrderModel> sortedOrdersByDate(List<OrderModel> orders) {
        List<OrderModel> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(OrderModel::getDataPedido).reversed())
                .collect(Collectors.toList());
        return sortedOrders;
    }
}
