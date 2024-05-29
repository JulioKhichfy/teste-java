package com.gerenciador.pedidos;

import com.gerenciador.pedidos.service.FileHandleService;
import com.gerenciador.pedidos.service.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FileFieldsTests {

	@InjectMocks
	private FileHandleService fileService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSemNumeroControleJSONFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemNumeroControle.json";
		String contentType = "application/json";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try {
			fileService.processFile(multipartFile);
		}catch(ControlNumberException e) {
			assertEquals("Erro: Número de controle ausente", e.getMessage());
		}
	}

	@Test
	public void testSemNumeroControleXMLFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemNumeroControle.xml";
		String contentType = "application/xml";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try {
			fileService.processFile(multipartFile);
		}catch(ControlNumberException e) {
			assertEquals("Erro: Número de controle ausente", e.getMessage());
		}
	}

	@Test
	public void testDataForaDoPadraoXMLFile() throws IOException {
		String path = "src/test/resources/TestFiles/dataForaDoPadrao.xml";
		String contentType = "application/xml";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (OrderDateException e) {
			assertEquals(e.getMessage(),"Erro: A data deve estar no formato 'dd-MM-yyyy' ");
		}
	}

	@Test
	public void testDataForaDoPadraoJSONFile() throws IOException {
		String path = "src/test/resources/TestFiles/dataForaDoPadrao.json";
		String contentType = "application/json";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (OrderDateException e) {
			assertEquals(e.getMessage(),"Erro: A data deve estar no formato 'dd-MM-yyyy' ");
		}
	}

	@Test
	public void testSemNomeProdutoJSONFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemNomeProduto.json";
		String contentType = "application/json";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ProductNameException e) {
			assertEquals(e.getMessage(),"Erro: Nome do produto ausente");
		}
	}

	@Test
	public void testSemNomeProdutoXMLFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemNomeProduto.xml";
		String contentType = "application/xml";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ProductNameException e) {
			assertEquals(e.getMessage(),"Erro: Nome do produto ausente");
		}
	}

	@Test
	public void testSemCodigoClienteXMLFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemCodigoCliente.xml";
		String contentType = "application/xml";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ClientCodeException e) {
			assertEquals(e.getMessage(),"Erro: Código do cliente ausente");
		}
	}

	@Test
	public void testSemCodigoClienteJSONFile() throws IOException {
		String path = "src/test/resources/TestFiles/SemCodigoCliente.json";
		String contentType = "application/json";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ClientCodeException e) {
			assertEquals(e.getMessage(),"Erro: Código do cliente ausente");
		}
	}

	@Test
	public void testNumeroControleRepetidoJSONFile() throws IOException {
		String path = "src/test/resources/TestFiles/NumeroControleRepetido.json";
		String contentType = "application/json";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ControlNumberExistsException e) {
			assertEquals(e.getMessage(),"Erro: Número de controle duplicado no arquivo");
		}
	}

	@Test
	public void testNumeroControleRepetidoXMLFile() throws IOException {
		String path = "src/test/resources/TestFiles/NumeroControleRepetido.xml";
		String contentType = "application/xml";
		MultipartFile multipartFile = getMultiPartFile(path, contentType);
		try{
			fileService.processFile(multipartFile);
		} catch (ControlNumberExistsException e) {
			assertEquals(e.getMessage(),"Erro: Número de controle duplicado no arquivo");
		}
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
