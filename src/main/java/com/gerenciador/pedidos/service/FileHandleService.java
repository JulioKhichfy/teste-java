package com.gerenciador.pedidos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderItemModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FileHandleService {

    private Logger logger = Logger.getLogger(FileHandleService.class.getName());

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderItemService orderItemService;

    public void ckeckFile(MultipartFile file)  {
        OrderListDTO orderListDTO = parserFileToDTO(file);
        checkFields(orderListDTO);
        checkDataBase(orderListDTO);
        List<ClientModel> clients = groupingByClientAndDate(orderListDTO);
        clientService.saveAll(clients);
    }

    private OrderListDTO parserFileToDTO(MultipartFile file){
        OrderListDTO orderListDTO = null;

        if(file == null) {
            throw new FileNullableException("Erro: O arquivo JSON ou XML não foi anexado.");
        }

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
            } else {
                throw new FileExtensionException("Erro: Apenas são permitidos arquivos dos tipos JSON ou XML.");
            }

            if (orderListDTO == null) {
                throw new PedidoNullableException("Erro: Verifique se o arquivo enviado encontra-se no padrão pré-definido");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PedidoException(e.getMessage(), e.getCause());
        }

        return orderListDTO;

    }

    private List<ClientModel> groupingByClientAndDate(OrderListDTO pedidoList){
        List<ClientModel> clientes = new ArrayList<>();

        //Obter todos os pedidos de um cliente
        Map<Integer, List<OrderDTO>> pedidosPorCliente = pedidoList.getPedidos().stream()
                .collect(Collectors.groupingBy(OrderDTO::getCodigoCliente));

        //para cada cliente, organizar seus pedidos por data
        pedidosPorCliente.forEach((codigoCliente, pedidosList) -> {
            ClientModel cli = prepararCliente(codigoCliente, pedidosList);
            clientes.add(cli);
        });

        return clientes;
    }

    /*@Transactional
    private void saveOrUpdateClientes(List<ClientModel> clientes){
        clienteService.saveAll(clientes);
    }*/

    private Map<String, List<OrderDTO>> pedidosClienteByData(List<OrderDTO> pedidosDTO){
        Map<String, List<OrderDTO>> pedidosPorData = new HashMap<>();
        for(OrderDTO pedidoDTO : pedidosDTO) {
            if (!pedidosPorData.containsKey(pedidoDTO.getDataCadastro())) {
                List<OrderDTO> pedidos = new ArrayList<>();
                pedidos.add(pedidoDTO);
                pedidosPorData.put(pedidoDTO.getDataCadastro(), pedidos);
            } else {
                List<OrderDTO> pedidosFromMap = pedidosPorData.get(pedidoDTO.getDataCadastro());
                pedidosFromMap.add(pedidoDTO);
                pedidosPorData.put(pedidoDTO.getDataCadastro(), pedidosFromMap);
            }
        }
        return pedidosPorData;
    }

    //cliente especifico e sua lista de pedidos
    private ClientModel prepararCliente(Integer codigoCliente, List<OrderDTO> pedidosDTO){
        ClientModel cliente = clientService.findByCodigo(codigoCliente);
        if(cliente == null) {
            cliente = new ClientModel();
            cliente.setCodigo(codigoCliente);
        }

        //pedidos do cliente especifico indexado por data
        Map<String, List<OrderDTO>> pedidosPorData = pedidosClienteByData(pedidosDTO);
        List<OrderModel> pedidosModel = new ArrayList<>();

        ClientModel finalCliente = cliente;
        pedidosPorData.forEach((dataPedido, pedidosList) -> {
            List<OrderItemModel> itens = new ArrayList<>();
            OrderModel pedido = new OrderModel();
            pedido.setCliente(finalCliente);
            pedido.setDataPedido(LocalDate.parse(dataPedido));
            double total = 0;
            int quantidadeTotal = 0;

            for(OrderDTO pedidoDTO : pedidosList){
                OrderItemModel item = new OrderItemModel();
                item.setPrecoUnitario(BigDecimal.valueOf(pedidoDTO.getValor()));
                item.setNome(pedidoDTO.getNome());
                quantidadeTotal += pedidoDTO.getQuantidade();
                item.setQuantidade(pedidoDTO.getQuantidade());
                item.setNumeroControle(pedidoDTO.getNumeroControle());
                item.setPedido(pedido);
                itens.add(item);
                total+=pedidoDTO.getValor() * pedidoDTO.getQuantidade();;
            }
            pedido.setItens(itens);
            BigDecimal pedidoTotal = aplicarDesconto(quantidadeTotal, total);
            pedido.setTotal(pedidoTotal);
            pedidosModel.add(pedido);
        });

        cliente.setPedidos(pedidosModel);
        return cliente;
    }

    private BigDecimal aplicarDesconto(Integer quantidadeTotal, double total){

        if(quantidadeTotal >=5 && quantidadeTotal <10){
            total = total * 0.95;
        }
        if(quantidadeTotal >=10){
            total = total * 0.90;
        }

        return BigDecimal.valueOf(total);
    }

    private void checkFields(OrderListDTO orderListDTO) {

        //O arquivo pode conter 1 ou mais pedidos, limitado a 10.
        if(orderListDTO.getPedidos().size() > 10) {
            throw new PedidoQuantidadeException("Erro: Favor informar no máximo 10 pedidos");
        }

        HashSet<Integer> set = new HashSet<>();
        orderListDTO.getPedidos().forEach(p -> {
            //Não poderá aceitar um número de controle já cadastrado
            if (!set.add(p.getNumeroControle())) {
                throw new ItemPedidoNumeroControleExistsException("Erro: Número de controle duplicado no arquivo");
            }
            checkControlNumber(p);
            checkDate(p);
            checkItemName(p);
            checkItemQuantity(p);
            checkClientCode(p);
        });
    }

    private void checkControlNumber(OrderDTO orderDTO) {
        //número controle é obrigatório
        if(orderDTO.getNumeroControle() == 0) {
            throw new PedidoNumeroControleException("Erro: Número de controle ausente");
        }
    }

    private void checkDate(OrderDTO orderDTO){
        boolean hasDate = false;

        //Caso a data de cadastro não seja enviada o sistema deve assumir a data atual.
        if(orderDTO.getDataCadastro() == null || orderDTO.getDataCadastro().isBlank()) {
            String dataStr = LocalDate.now().toString();
            orderDTO.setDataCadastro(dataStr);
            hasDate = true;
        }
        try {
            if(!hasDate) LocalDate.parse(orderDTO.getDataCadastro());
        } catch(DateTimeParseException e) {
            throw new PedidoDateException("Erro: A data deve estar no formato 'dd-MM-yyyy' ", e.getCause());
        }
    }

    private void checkItemName(OrderDTO orderDTO){
        //nome do produto obrigatório
        if(orderDTO.getNome() == null || orderDTO.getNome().isBlank()) {
            throw new PedidoNomeProdutoException("Erro: Nome do produto ausente");
        }
    }

    private void checkItemQuantity(OrderDTO orderDTO){
        //Caso a quantidade não seja enviada considerar 1.
        if(orderDTO.getQuantidade() == 0){
            orderDTO.setQuantidade(1);
        }
    }

    private void checkClientCode(OrderDTO orderDTO){
        //codigo cliente obrigatório.
        if(orderDTO.getCodigoCliente() == 0){
            throw new PedidoCodigoClienteException("Erro: Código do cliente ausente");
        }
    }

    public void checkDataBase(OrderListDTO orderListDTO) {
        orderListDTO.getPedidos().forEach(order -> {
            //Não poderá aceitar um número de controle já cadastrado.
            if(orderItemService.existsByNumeroControle(order.getNumeroControle())){
                throw new ItemPedidoNumeroControleExistsException("Erro: Número de controle informado já existe no banco de dados");
            }
        });
    }
}
