package com.gerenciador.pedidos.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.PedidoListDTO;
import com.gerenciador.pedidos.repository.PedidosRepository;
import com.gerenciador.pedidos.service.ArquivoPedidoService;
import com.gerenciador.pedidos.service.PedidoService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoResource {

    @Autowired
    private ArquivoPedidoService arquivoPedidoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        arquivoPedidoService.processarArquivo(file);
        return ResponseEntity.ok(201);
    }
}

