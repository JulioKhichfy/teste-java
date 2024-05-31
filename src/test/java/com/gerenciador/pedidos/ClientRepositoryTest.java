/*package com.gerenciador.pedidos;
import com.gerenciador.pedidos.repository.ClientRepository;
import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.exceptions.ClientCodeException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @InjectMocks
    private FileHandleService fileService;

    @Test
    @Transactional
    @Rollback(false) // Optionally, you can disable rollback to inspect the DB after test
    public void testSaveClient() throws IOException{

        String path = "src/test/resources/TestFiles/SemCodigoCliente.json";
        String contentType = "application/json";
        MultipartFile multipartFile = getMultiPartFile(path, contentType);
        try{
            fileService.processFile(multipartFile);
        } catch (ClientCodeException e) {
            assertEquals(e.getMessage(),"Erro: Código do cliente ausente");
        }

        // Cria um novo cliente
        ClientModel client = new ClientModel();
        client.setCodigo(123);

        // Salva o cliente no repositório
        client = clientRepository.save(client);

        // Verifica se o cliente foi salvo corretamente
        assertThat(client.getId()).isNotNull();
        assertThat(clientRepository.findById(client.getId())).isPresent();
    }

    private MultipartFile getMultiPartFile(String filePath, String contentType) throws IOException {
        // Caminho do arquivo de teste no pacote de testes
        Path path = Paths.get(filePath);
        String originalFileName = filePath.split("/")[4];
        byte[] content = Files.readAllBytes(path);

        // Cria um MockMultipartFile com o conteúdo do arquivo de teste
        MultipartFile multipartFile = new MockMultipartFile(originalFileName, originalFileName, contentType, content);
        return multipartFile;
    }
}
*/