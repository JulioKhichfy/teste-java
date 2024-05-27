package com.gerenciador.pedidos;

import com.gerenciador.pedidos.service.ArquivoPedidoService;
import com.gerenciador.pedidos.service.exceptions.FileNullableException;
import com.gerenciador.pedidos.service.exceptions.PedidoNumeroControleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PedidosApplicationTests {

	@InjectMocks
	private ArquivoPedidoService fileService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSemNumeroControleJSONFile() throws IOException {
		// Caminho do arquivo de teste no pacote de testes
		Path path = Paths.get("src/test/resources/pedidosSemNumeroControle.json");
		String originalFileName = "pedidosSemNumeroControle.json";
		String contentType = "application/json";
		byte[] content = Files.readAllBytes(path);

		// Cria um MockMultipartFile com o conteúdo do arquivo de teste
		MultipartFile multipartFile = new MockMultipartFile(originalFileName, originalFileName, contentType, content);

		// Chama o método do serviço
		try {
			fileService.processarArquivo(multipartFile);
		}catch(PedidoNumeroControleException e) {
			assertEquals("Erro: Número de controle ausente", e.getMessage());
		}

	}

	@Test
	public void testSemNumeroControleXMLFile() throws IOException {
		// Caminho do arquivo de teste no pacote de testes
		Path path = Paths.get("src/test/resources/pedidosSemNumeroControle.xml");
		String originalFileName = "pedidosSemNumeroControle.xml";
		String contentType = "application/xml";
		byte[] content = Files.readAllBytes(path);

		// Cria um MockMultipartFile com o conteúdo do arquivo de teste
		MultipartFile multipartFile = new MockMultipartFile(originalFileName, originalFileName, contentType, content);

		// Chama o método do serviço
		try {
			fileService.processarArquivo(multipartFile);
		}catch(PedidoNumeroControleException e) {
			assertEquals("Erro: Número de controle ausente", e.getMessage());
		}

	}

}
