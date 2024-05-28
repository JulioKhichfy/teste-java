package com.gerenciador.pedidos;
import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.repository.ClientRepository;
import com.gerenciador.pedidos.repository.OrderItemRepository;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.ClientService;
import com.gerenciador.pedidos.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles("test")
public class FileServiceTest {

    @Autowired
    private ClientRepository clientRepository;
    private ClientService clientService;

    @Autowired
    private OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    @InjectMocks
    private FileHandleService fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        clientService = new ClientService(clientRepository);
        orderItemService = new OrderItemService(orderItemRepository);
    }

    @Test
    public void testSaveFileJSON() throws IOException{
        String path = "src/test/resources/TestFiles/pedidos.json";
        String contentType = "application/json";
        MultipartFile multipartFile = getMultiPartFile(path, contentType);
        fileService.ckeckFile(multipartFile);
        List<ClientModel> foundClientes = clientService.findAll();
        assertEquals(10, foundClientes.size());
        //assertEquals(123, foundClientes.get(0).getCodigo());
    }

    private MultipartFile getMultiPartFile(String filePath, String contentType) throws IOException{
        // Caminho do arquivo de teste no pacote de testes
        Path path = Paths.get(filePath);
        String originalFileName = filePath.split("/")[4];
        byte[] content = Files.readAllBytes(path);

        // Cria um MockMultipartFile com o conteúdo do arquivo de teste
        MultipartFile multipartFile = new MockMultipartFile(originalFileName, originalFileName, contentType, content);
        return multipartFile;
    }
}
