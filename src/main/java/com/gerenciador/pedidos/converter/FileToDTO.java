package com.gerenciador.pedidos.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.OrderListDTO;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileToDTO {

    public static OrderListDTO convertFileToDTO(MultipartFile file) {
        OrderListDTO orderListDTO = null;
        if(file == null) throw new FileNullableException("Erro: O arquivo JSON ou XML não foi anexado.");
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
            } else throw new FileExtensionException("Erro: Apenas são permitidos arquivos dos tipos JSON ou XML.");

            if (orderListDTO == null) throw new OrderNullableException("Erro: Verifique se o arquivo enviado encontra-se no padrão pré-definido");

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderException(e.getMessage(), e.getCause());
        }
        return orderListDTO;
    }
}
