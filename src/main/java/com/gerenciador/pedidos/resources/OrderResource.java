package com.gerenciador.pedidos.resources;

import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.model.OrderModel;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.OrderService;
import com.gerenciador.pedidos.util.MediaType;
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
    public List<OrderDTO> uploadFile(@RequestBody MultipartFile file) {
        List<ClientModel> clients = fileHandleService.processFile(file);
        orderService.checkNumeroControleInDataBase(clients);
        clientService.saveOrUpdate(clients);
        return orderService.findAll();
    }

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping(value="/{numeroControle}", produces = { MediaType.APPLICATION_JSON })
    public OrderDTO findByNumeroControle(@PathVariable Integer numeroControle) {
        return orderService.findByNumeroControle(numeroControle);
    }

    @GetMapping(value="/data/{dataPedido}", produces = { MediaType.APPLICATION_JSON })
    public List<OrderDTO> findByDataPedido(@PathVariable String dataPedido) {
        return orderService.findByDataPedido(dataPedido);
    }

}

