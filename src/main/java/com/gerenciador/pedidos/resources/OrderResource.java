package com.gerenciador.pedidos.resources;

import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.FileHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        fileHandleService.ckeckFile(file);
        return ResponseEntity.ok(201);
    }
}

