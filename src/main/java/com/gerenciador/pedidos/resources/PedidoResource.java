package com.gerenciador.pedidos.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.model.PedidosArquivo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
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

    @PostMapping("/upload")
    public ResponseEntity<PedidosArquivo> uploadFile(@RequestBody MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            PedidosArquivo pedidosArquivo = null;

            if (filename != null && (filename.endsWith(".json") || filename.endsWith(".JSON"))) {
                ObjectMapper objectMapper = new ObjectMapper();
                pedidosArquivo = objectMapper.readValue(inputStream, PedidosArquivo.class);
            } else if(filename != null && (filename.endsWith(".xml") || filename.endsWith(".XML"))) {
                JAXBContext jaxbContext = JAXBContext.newInstance(PedidosArquivo.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                pedidosArquivo = (PedidosArquivo) unmarshaller.unmarshal(inputStream);
            }

            if (pedidosArquivo != null) {
                return new ResponseEntity<>(pedidosArquivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

