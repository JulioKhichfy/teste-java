package com.gerenciador.pedidos.converter;

import com.gerenciador.pedidos.dto.ItemDTO;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.model.OrderModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDtoToModel {

    private List<ClientModel> groupingByClientAndDate(OrderListDTO orderListDTO){
        List<ClientModel> clients = new ArrayList<>();

        //Obter todos os pedidos de um cliente
        Map<Integer, List<OrderDTO>> ordersByClient = orderListDTO.getPedidos().stream()
                .collect(Collectors.groupingBy(OrderDTO::getCodigoCliente));

        ordersByClient.forEach((codigoCliente, ordersDTO) -> {
            ClientModel clientModel = new ClientModel();
            clientModel.setCodigo(codigoCliente);
            List<OrderModel> orders = new ArrayList<>();

            for(OrderDTO orderDTO : ordersDTO) {
                OrderModel orderModel = new OrderModel();
                orderModel.setNumeroControle(orderDTO.getNumeroControle());
                orderModel.setDataPedido(LocalDate.parse(orderDTO.getDataCadastro()));
                orderModel.setCliente(clientModel);

                List<OrderItemModel> items = new ArrayList<>();
                double totalPriceOrder = 0;
                for(ItemDTO item : orderDTO.getItems()) {
                    OrderItemModel itemModel = new OrderItemModel();
                    int quantidade = item.getQuantidade();
                    double precoUnitario = item.getValor();
                    itemModel.setNome(item.getNome());
                    itemModel.setQuantidade(quantidade);
                    itemModel.setPrecoUnitario(BigDecimal.valueOf(precoUnitario));
                    itemModel.setPedido(orderModel);
                    double total = quantidade * precoUnitario;
                    if(quantidade >= 5 && quantidade < 10) {
                        total = total * 0.95;
                        itemModel.setPrecoTotal(BigDecimal.valueOf(total));
                    }
                    if(quantidade >= 10) {
                        total = total * 0.90;
                        itemModel.setPrecoTotal(BigDecimal.valueOf(total * 0.90));
                    }
                    itemModel.setPrecoTotal(BigDecimal.valueOf(total));
                    totalPriceOrder+=total;
                    items.add(itemModel);
                }
                orderModel.setTotal(BigDecimal.valueOf(totalPriceOrder));
                orderModel.setItens(items);
                orders.add(orderModel);
            }
            clientModel.setPedidos(orders);
            clients.add(clientModel);
        });
        return clients;
    }
}
