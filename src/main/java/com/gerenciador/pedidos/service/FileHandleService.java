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
import java.util.logging.Logger;

@Service
public class FileHandleService {

    private Logger logger = Logger.getLogger(FileHandleService.class.getName());

    public List<ClientModel> processFile(MultipartFile file)  {

        OrderListDTO orderListDTO = Converter.fileToDTO(file);
        checkFields(orderListDTO);
        List<ClientModel> clients = Converter.DtoToModel(orderListDTO);
        return clients;
    }

    private void checkFields(OrderListDTO orderListDTO) {

        //O arquivo pode conter 1 ou mais pedidos, limitado a 10.
        if(orderListDTO.getPedidos().size() > 10) {
            throw new OrderSizeException("Erro: Favor informar no máximo 10 pedidos");
        }

        HashSet<Integer> set = new HashSet<>();
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
        if(orderDTO.getNumeroControle() == 0) {
            throw new ControlNumberException("Erro: Número de controle ausente");
        }

        //Não poderá aceitar um número de controle repetido no arquivo
        if (!set.add(orderDTO.getNumeroControle())) {
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
            throw new OrderDateException("Erro: A data deve estar no formato 'dd-MM-yyyy' ", e.getCause());
        }
    }

    private void checkItemName(OrderDTO orderDTO) {
        //nome do produto obrigatório
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

    private void checkItemQuantity(OrderDTO orderDTO){
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
        if(orderDTO.getCodigoCliente() == 0){
            throw new ClientCodeException("Erro: Código do cliente ausente");
        }
    }


}
