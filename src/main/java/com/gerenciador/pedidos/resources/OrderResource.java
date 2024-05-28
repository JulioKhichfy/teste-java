package com.gerenciador.pedidos.resources;

import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.FileHandleService;
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

    @PostMapping("/upload")
    public List<ClientModel> uploadFile(@RequestBody MultipartFile file) {
        fileHandleService.ckeckAndSave(file);
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

