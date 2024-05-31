package com.gerenciador.pedidos.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gerenciador.pedidos.dto.ItemDTO;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(Converter.class);

    public static OrderListDTO buildDTOFromFile(MultipartFile file) {
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

        } catch (UnrecognizedPropertyException e) {
            e.printStackTrace();
            throw new OrderNullableException("Erro: Verifique se o arquivo enviado encontra-se no padrão pré-definido", e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderException(e.getMessage(), e.getCause());
        }
        return orderListDTO;
    }

    public static List<ClientModel> DtoFromFileToModel(OrderListDTO orderListDTO){
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
                        logger.info("Desconto de 5% aplicado para o item {} do pedido {} ", item.getNome(), orderDTO.getNumeroControle());
                        total = total * 0.95;
                        itemModel.setPrecoTotal(BigDecimal.valueOf(total));
                    }
                    if(quantidade >= 10) {
                        logger.info("Desconto de 10% aplicado para o item {} do pedido {} ", item.getNome(), orderDTO.getNumeroControle());
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

    public static OrderDTO convertOrderModelToOrderDTO(OrderModel order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumeroControle(order.getNumeroControle());
        orderDTO.setDataCadastro(order.getDataPedido().toString());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setItems(convertItemModelToItemDTO(order.getItens()));
        orderDTO.setCodigoCliente(order.getCliente().getCodigo());
        return orderDTO;
    }

    public static List<ItemDTO> convertItemModelToItemDTO(List<OrderItemModel> items) {
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

    public static List<OrderDTO> buildListOrderDTO(List<OrderModel> orders){
        List<OrderDTO> ordersDTO = new ArrayList<>();
        orders.forEach(o -> {
            ordersDTO.add(convertOrderModelToOrderDTO(o));
        });
        return ordersDTO;
    }
}
