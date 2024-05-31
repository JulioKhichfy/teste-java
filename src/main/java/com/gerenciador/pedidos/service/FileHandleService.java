package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.converter.Converter;
import com.gerenciador.pedidos.dto.ItemDTO;
import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.service.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileHandleService {

    private static final Logger logger = LoggerFactory.getLogger(FileHandleService.class);

    public List<ClientModel> processFile(MultipartFile file) {
        logger.info("Processar arquivo {}", file.getName());

        //Converte o arquivo xml ou json para o objeto DTO.
        OrderListDTO orderListDTO = Converter.buildOrdersDTOFromFile(file);

        //Validar e/ou customizar os campos do objeto DTO
        checkFields(orderListDTO);

        //Construir o objeto ClientModel com pedidos com/sem desconto
        List<ClientModel> clients = Converter.ordersDTOToClientModel(orderListDTO);

        logger.info("Arquivo {} processado sem erros", file.getName());
        return clients;
    }

    private void checkFields(OrderListDTO orderListDTO) {
        //O arquivo pode conter 1 ou mais pedidos, limitado a 10.
        int quantity = orderListDTO.getPedidos().size();
        if(quantity > 10) {
            logger.error("Erro: o arquivo possui {} pedidos. Apenas 10 são permitidos", quantity);
            throw new OrderSizeException("Erro: Favor informar no máximo 10 pedidos");
        }

        //HashSet não permite duplicado. Será útil para checar numero de controle repetido no arquivo
        HashSet<Integer> set = new HashSet<>();

        //Para cada pedido verificar os campos
        orderListDTO.getPedidos().forEach(orderDTO -> {
            checkControlNumber(orderDTO, set);
            checkDate(orderDTO);
            checkItemName(orderDTO);
            checkItemQuantity(orderDTO);
            checkClientCode(orderDTO);
        });
    }

    private void checkControlNumber(OrderDTO orderDTO, HashSet<Integer> set) {
        //número controle é obrigatório
        if(orderDTO.getNumeroControle() == 0) throw new ControlNumberException("Erro: Número de controle ausente");

        // Tentativa de inserir numero de controle no Set.
        // Caso não consiga inserir então o numero de controle já existe.
        if (!set.add(orderDTO.getNumeroControle())) {
            logger.error("Erro: O número de controle {} está repetido no arquivo ", orderDTO.getNumeroControle());
            throw new ControlNumberExistsException("Erro: Número de controle duplicado no arquivo");
        }
    }

    private void checkDate(OrderDTO orderDTO) {
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
            logger.error("Erro: A data {} não está no padrão. O padrão é 'dd-MM-yyyy' ", orderDTO.getDataCadastro());
            throw new OrderDateException("Erro: A data deve estar no formato 'dd-MM-yyyy' ", e.getCause());
        }
    }

    private void checkItemName(OrderDTO orderDTO) {
        //Primeiramente verificar se existem items
        List<ItemDTO> items = orderDTO.getItems();
        if(items == null) {
            throw new ProductNameException("Erro: Não existem items no pedido");
        }

        for(ItemDTO itemDTO : items) {
            if(itemDTO.getNome() == null || itemDTO.getNome().isBlank()) {
                throw new ProductNameException("Erro: Nome do produto ausente");
            }
        }
    }

    private void checkItemQuantity(OrderDTO orderDTO) {
        //Caso a quantidade não seja enviada considerar 1.
        List<ItemDTO> items = orderDTO.getItems();

        for(ItemDTO itemDTO : items) {
            if(itemDTO.getQuantidade() == 0) {
                itemDTO.setQuantidade(1);
            }
        }
    }

    private void checkClientCode(OrderDTO orderDTO){
        //codigo cliente obrigatório.
        if(orderDTO.getCodigoCliente() == 0) {
            logger.error("Error: O pedido de número de controle {} não possui código do cliente", orderDTO.getNumeroControle());
            throw new ClientCodeException("Erro: Código do cliente ausente");
        }
    }
}