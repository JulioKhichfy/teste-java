package com.gerenciador.pedidos.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.ItemDTO;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Converter {

    public static OrderListDTO fileToDTO(MultipartFile file) {
        OrderListDTO orderListDTO = null;
        if(file == null) throw new FileNullableException("Erro: O arquivo JSON ou XML não foi anexado.");
        try {
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            if (filename != null && (filename.endsWith(".json") || filename.endsWith(".JSON"))) {
                ObjectMapper objectMapper = new ObjectMapper();
                orderListDTO = objectMapper.readValue(inputStream, OrderListDTO.class);
            } else if (filename != null && (filename.endsWith(".xml") || filename.endsWith(".XML"))) {
                JAXBContext jaxbContext = JAXBContext.newInstance(OrderListDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                orderListDTO = (OrderListDTO) unmarshaller.unmarshal(inputStream);
            } else throw new FileExtensionException("Erro: Apenas são permitidos arquivos dos tipos JSON ou XML.");

            if (orderListDTO == null) throw new OrderNullableException("Erro: Verifique se o arquivo enviado encontra-se no padrão pré-definido");

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderException(e.getMessage(), e.getCause());
        }
        return orderListDTO;
    }

    public static List<ClientModel> DtoToModel(OrderListDTO orderListDTO){
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
