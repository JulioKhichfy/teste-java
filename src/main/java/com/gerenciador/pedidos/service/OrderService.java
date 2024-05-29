package com.gerenciador.pedidos.service;
import com.gerenciador.pedidos.dto.ItemDTO;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.repository.OrderRepository;
import com.gerenciador.pedidos.service.exceptions.ControlNumberExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public OrderDTO findByNumeroControle(Integer numeroControle){
        OrderModel order = orderRepository.findByNumeroControle(numeroControle);
        return convertOrderModelToDTO(order);
    }

    public List<OrderDTO> findByDataPedido(String dataPedido){
        List<OrderModel> orders = orderRepository.findByDataPedido(LocalDate.parse(dataPedido));
        return buildListOrderDTO(orders);
    }

    public List<OrderDTO> findAll(){
        List<OrderModel> orders = orderRepository.findAll();
        return buildListOrderDTO(orders);
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

    private OrderDTO convertOrderModelToDTO(OrderModel order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumeroControle(order.getNumeroControle());
        orderDTO.setDataCadastro(order.getDataPedido().toString());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setItems(convertItemModelToDTO(order.getItens()));
        orderDTO.setCodigoCliente(order.getCliente().getCodigo());
        return orderDTO;
    }

    private List<ItemDTO> convertItemModelToDTO(List<OrderItemModel> items) {
        List<ItemDTO> itemListDTO = new ArrayList<>();
        items.forEach(item -> {
            ItemDTO i = new ItemDTO();
            i.setQuantidade(item.getQuantidade());
            i.setNome(item.getNome());
            i.setValor(item.getPrecoUnitario().doubleValue());
            i.setValorTotal(item.getPrecoTotal().doubleValue());
            itemListDTO.add(i);
        });

        return itemListDTO;
    }

    private List<OrderDTO> buildListOrderDTO(List<OrderModel> orders){
        List<OrderDTO> ordersDTO = new ArrayList<>();
        orders.forEach(o -> {
            ordersDTO.add(convertOrderModelToDTO(o));
        });
        return ordersDTO;
    }

}
