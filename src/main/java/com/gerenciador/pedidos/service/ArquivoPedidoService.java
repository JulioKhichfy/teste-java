package com.gerenciador.pedidos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.PedidoDTO;
import com.gerenciador.pedidos.dto.PedidoListDTO;
import com.gerenciador.pedidos.model.ClienteModel;
import com.gerenciador.pedidos.model.ItemPedidoModel;
import com.gerenciador.pedidos.model.ProdutoModel;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ArquivoPedidoService {

    private Logger logger = Logger.getLogger(ArquivoPedidoService.class.getName());

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    public void processarArquivo(MultipartFile file)  {
        PedidoListDTO pedidoList = parserFileToDTO(file);
        checkFields(pedidoList);
        List<ClienteModel> clientes = groupingByClientAndDate(pedidoList);
        saveOrUpdateClientes(clientes);
    }

    private PedidoListDTO parserFileToDTO(MultipartFile file){
        PedidoListDTO pedidoList = null;

        if(file == null) {
            throw new FileNullableException("Erro: O arquivo JSON ou XML não foi anexado.");
        }

        try {
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();

            if (filename != null && (filename.endsWith(".json") || filename.endsWith(".JSON"))) {
                ObjectMapper objectMapper = new ObjectMapper();
                pedidoList = objectMapper.readValue(inputStream, PedidoListDTO.class);
            } else if (filename != null && (filename.endsWith(".xml") || filename.endsWith(".XML"))) {
                JAXBContext jaxbContext = JAXBContext.newInstance(PedidoListDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                pedidoList = (PedidoListDTO) unmarshaller.unmarshal(inputStream);
            } else {
                throw new FileExtensionException("Erro: Apenas são permitidos arquivos dos tipos JSON ou XML.");
            }

            if (pedidoList == null) {
                throw new PedidoNullableException("Erro: Verifique se o arquivo enviado encontra-se no padrão pré-definido");
            }
        } catch (IOException e) {
            e.printStackTrace();
            /*if(e.getMessage().contains("numeroControle")){
                throw new IOPedidoException("Número de controle inválido", e.getCause());
            }*/
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PedidoException(e.getMessage(), e.getCause());
        }

        return pedidoList;

    }

    private List<ClienteModel> groupingByClientAndDate(PedidoListDTO pedidoList){
        List<ClienteModel> clientes = new ArrayList<>();

        //Obter todos os pedidos de um cliente
        Map<Integer, List<PedidoDTO>> pedidosPorCliente = pedidoList.getPedidos().stream()
                .collect(Collectors.groupingBy(PedidoDTO::getCodigoCliente));

        //para cada cliente, organizar seus pedidos por data
        pedidosPorCliente.forEach((codigoCliente, pedidosList) -> {
            ClienteModel cli = prepararCliente(codigoCliente, pedidosList);
            clientes.add(cli);
        });

        return clientes;
    }

    @Transactional
    private void saveOrUpdateClientes(List<ClienteModel> clientes){
        clienteService.saveAll(clientes);
    }

    private Map<String, List<PedidoDTO>> pedidosClienteByData(List<PedidoDTO> pedidosDTO){
        Map<String, List<PedidoDTO>> pedidosPorData = new HashMap<>();
        for(PedidoDTO pedidoDTO : pedidosDTO) {
            if (!pedidosPorData.containsKey(pedidoDTO.getDataCadastro())) {
                List<PedidoDTO> pedidos = new ArrayList<>();
                pedidos.add(pedidoDTO);
                pedidosPorData.put(pedidoDTO.getDataCadastro(), pedidos);
            } else {
                List<PedidoDTO> pedidosFromMap = pedidosPorData.get(pedidoDTO.getDataCadastro());
                pedidosFromMap.add(pedidoDTO);
                pedidosPorData.put(pedidoDTO.getDataCadastro(), pedidosFromMap);
            }
        }
        return pedidosPorData;
    }

    //cliente especifico e sua lista de pedidos
    private ClienteModel prepararCliente(Integer codigoCliente, List<PedidoDTO> pedidosDTO){
        ClienteModel cliente = clienteService.findByCodigo(codigoCliente);
        if(cliente == null) {
            cliente = new ClienteModel();
            cliente.setCodigo(codigoCliente);
        }

        //pedidos do cliente especifico indexado por data
        Map<String, List<PedidoDTO>> pedidosPorData = pedidosClienteByData(pedidosDTO);
        List<ProdutoModel> pedidosModel = new ArrayList<>();

        ClienteModel finalCliente = cliente;
        pedidosPorData.forEach((dataPedido, pedidosList) -> {
            List<ItemPedidoModel> itens = new ArrayList<>();
            ProdutoModel pedido = new ProdutoModel();
            pedido.setCliente(finalCliente);
            pedido.setDataPedido(LocalDate.parse(dataPedido));
            double total = 0;
            int quantidadeTotal = 0;

            for(PedidoDTO pedidoDTO : pedidosList){
                ItemPedidoModel item = new ItemPedidoModel();
                item.setPrecoUnitario(BigDecimal.valueOf(pedidoDTO.getValor()));
                item.setNome(pedidoDTO.getNome());
                quantidadeTotal += pedidoDTO.getQuantidade();
                item.setQuantidade(pedidoDTO.getQuantidade());
                item.setNumeroControle(pedidoDTO.getNumeroControle());
                item.setPedido(pedido);
                itens.add(item);
                total+=pedidoDTO.getValor() * pedidoDTO.getQuantidade();;
            }
            pedido.setItens(itens);
            BigDecimal pedidoTotal = aplicarDesconto(quantidadeTotal, total);
            pedido.setTotal(pedidoTotal);
            pedidosModel.add(pedido);
        });

        cliente.setPedidos(pedidosModel);
        return cliente;
    }

    private BigDecimal aplicarDesconto(Integer quantidadeTotal, double total){

        if(quantidadeTotal >=5 && quantidadeTotal <10){
            total = total * 0.95;
        }
        if(quantidadeTotal >=10){
            total = total * 0.90;
        }

        return BigDecimal.valueOf(total);
    }

    private void checkFields(PedidoListDTO pedidoList){
        if(pedidoList.getPedidos().size() > 10){
            throw new PedidoQuantidadeException("Erro: Favor informar no máximo 10 pedidos");
        }

        pedidoList.getPedidos().forEach(p -> {
            checkNumeroControle(p);
            checkDataCadastro(p);
            checkNomeProduto(p);
            checkQuantidadeProduto(p);
            checkCodigoCliente(p);
        });
    }

    private void checkNumeroControle(PedidoDTO pedido){
        if(pedido.getNumeroControle() == 0){
            throw new PedidoNumeroControleException("Erro: Número de controle ausente");
        }

        if(itemPedidoService.existsByNumeroControle(pedido.getNumeroControle())){
            throw new ItemPedidoNumeroControleExistsException("Erro: Número de controle informado já existe");
        }
    }

    private void checkDataCadastro(PedidoDTO pedido){
        if(pedido.getDataCadastro() == null){
            String dataStr = LocalDate.now().toString();
            pedido.setDataCadastro(dataStr);
        }

        try {
            LocalDate.parse(pedido.getDataCadastro());
        } catch(DateTimeParseException e) {
            throw new PedidoDateException("Erro: A data deve estar no formato 'dd-MM-yyyy' ", e.getCause());
        }
    }

    private void checkNomeProduto(PedidoDTO pedido){
        if(pedido.getNome() == null){
            throw new PedidoNomeProdutoException("Erro: Nome do produto ausente");
        }
    }

    private void checkQuantidadeProduto(PedidoDTO pedido){
        if(pedido.getQuantidade() == 0){
            pedido.setQuantidade(1);
        }
    }

    private void checkCodigoCliente(PedidoDTO pedido){
        if(pedido.getCodigoCliente() == 0){
            throw new PedidoCodigoClienteException("Erro: Código do cliente ausente");
        }
    }

}
