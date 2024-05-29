package com.gerenciador.pedidos.resources;

import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class OrderResource {

    @Autowired
    private FileHandleService fileHandleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/upload")
    public List<ClientModel> uploadFile(@RequestBody MultipartFile file) {
        List<ClientModel> clients = fileHandleService.processFile(file);
        orderService.checkNumeroControleInDataBase(clients);
        clientService.saveOrUpdate(clients);
        return clientService.findAll();
    }


    /*Criar um serviço onde possa consultar os pedidos enviados pelos clientes.
    Critérios aceitação:

    O retorno deve trazer todos os dados do pedido.

    filtros da consulta:
    número pedido, data cadastro, todos*/

    @GetMapping
    public List<ClientModel> findAll() {
        return clientService.findAll();
    }

}

