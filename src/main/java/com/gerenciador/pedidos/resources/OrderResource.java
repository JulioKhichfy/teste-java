package com.gerenciador.pedidos.resources;

import com.gerenciador.pedidos.dto.OrderDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.OrderService;
import com.gerenciador.pedidos.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedido", description = "Endpoints para gerenciar pedidos")
public class OrderResource {

    @Autowired
    private FileHandleService fileHandleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload a file", description = "Uploads a file to the server")
    public List<OrderDTO> uploadFile(
            @Parameter(description = "File to upload", content = @Content(mediaType = "application/octet-stream"))
            @RequestPart("file") MultipartFile file) {
        List<ClientModel> clients = fileHandleService.processFile(file);
        orderService.checkControlNumberInDataBase(clients);
        clientService.saveOrUpdate(clients);
        return orderService.findAll();
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns the orders")
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping(value="/{numeroControle}", produces = { MediaType.APPLICATION_JSON })
    @Operation(summary = "Get order by control number", description = "Return the order by control number")
    public OrderDTO findByControNumber(@PathVariable Integer numeroControle) {
        return orderService.findByControNumber(numeroControle);
    }

    @GetMapping(value="/data/{dataPedido}", produces = { MediaType.APPLICATION_JSON })
    @Operation(summary = "Get order by date", description = "Return the order by date")
    public List<OrderDTO> findByDate(@PathVariable String dataPedido) {
        return orderService.findByDate(dataPedido);
    }

}

