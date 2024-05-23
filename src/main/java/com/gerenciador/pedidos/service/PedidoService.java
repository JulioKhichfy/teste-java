package com.gerenciador.pedidos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.pedidos.dto.PedidoDTO;
import com.gerenciador.pedidos.dto.PedidoListDTO;
import com.gerenciador.pedidos.model.ClienteModel;
import com.gerenciador.pedidos.model.ItemPedidoModel;
import com.gerenciador.pedidos.model.PedidoModel;
import com.gerenciador.pedidos.model.ProdutoModel;
import com.gerenciador.pedidos.repository.PedidosRepository;
import com.gerenciador.pedidos.service.exceptions.*;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private Logger logger = Logger.getLogger(PedidoService.class.getName());

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    public void processarArquivo(MultipartFile file){
        PedidoListDTO pedidoList = null;
        try {
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();

            if (filename != null && (filename.endsWith(".json") || filename.endsWith(".JSON"))) {
                ObjectMapper objectMapper = new ObjectMapper();
                pedidoList = objectMapper.readValue(inputStream, PedidoListDTO.class);
            } else if(filename != null && (filename.endsWith(".xml") || filename.endsWith(".XML"))) {
                JAXBContext jaxbContext = JAXBContext.newInstance(PedidoListDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                pedidoList = (PedidoListDTO) unmarshaller.unmarshal(inputStream);
            }
            if (pedidoList == null) {
                throw new PedidoNullableException("Verifique se o arquivo enviado encontra-se no padrão pré-definido");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOPedidoException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PedidoException(e.getMessage(), e.getCause());
        }

        validarListaPedido(pedidoList);
        //Obter todos os codigos de clientes
        Map<Integer, List<PedidoDTO>> pedidosPorCliente = pedidoList.getPedidos().stream()
                .collect(Collectors.groupingBy(PedidoDTO::getCodigoCliente));

        pedidosPorCliente.forEach((codigoCliente, pedidosList) -> {
            saveOrUpdateCliente(codigoCliente, pedidosList);
        });
    }

    @Transactional
    private void saveOrUpdateCliente(Integer codigoCliente, List<PedidoDTO> pedidosDTO){

        //verifica se cliente já é cadastrado
        ClienteModel cliente = clienteService.findClienteFetchPedidos(codigoCliente);
        if(cliente != null) {
            List<PedidoModel> pedidosModel = new ArrayList<>();
            List<ItemPedidoModel> itemsPedidosModel = new ArrayList<>();
            PedidoModel pedidoModel = null;
            for(PedidoDTO pedidoDTO : pedidosDTO){
                pedidoModel = new PedidoModel();
                pedidoModel.setCliente(cliente);
                pedidoModel.setDataPedido(LocalDate.parse(pedidoDTO.getDataCadastro()));
                pedidoModel.setNumeroControle(pedidoDTO.getNumeroControle());
                BigDecimal total = aplicarDesconto(pedidoDTO);
                pedidoModel.setTotal(total);

                ProdutoModel produtoModel = new ProdutoModel();
                produtoModel.setDescricao(pedidoDTO.getNome());
                produtoModel.setPreco(BigDecimal.valueOf(pedidoDTO.getValor()));
                produtoService.create(produtoModel);

                ItemPedidoModel itemPedidoModel = new ItemPedidoModel();
                itemPedidoModel.setProduto(produtoModel);
                itemPedidoModel.setQuantidade(pedidoDTO.getQuantidade());
                //itemPedidoModel.setPedido(pedidoModel);
                pedidoModel.setItem(itemPedidoModel);

                itemPedidoService.create(itemPedidoModel);
                pedidosRepository.save(pedidoModel);
                itemPedidoModel.setPedido(pedidoModel);
                itemPedidoService.create(itemPedidoModel);

                pedidosModel.add(pedidoModel);

            };
            //pedidoModel.setItens(itemsPedidosModel);
            cliente.setPedidos(pedidosModel);

            clienteService.create(cliente);


        }

    }

    private BigDecimal aplicarDesconto(PedidoDTO pedidoDTO){

        double total = (pedidoDTO.getQuantidade() * pedidoDTO.getValor());
        if(pedidoDTO.getQuantidade() >=5 && pedidoDTO.getQuantidade() <10){
            total = total * 0.95;
        }
        else if(pedidoDTO.getQuantidade() >= 10){
            total = total * 0.90;
        }

        return BigDecimal.valueOf(total);
    }

    private void validarListaPedido(PedidoListDTO pedidoList){
        if(pedidoList.getPedidos().size() > 10){
            throw new PedidoQuantidadeException("Favor informar no máximo 10 pedidos");
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
           throw new PedidoNumeroControleException("Número de controle ausente");
        }

        if(pedidosRepository.existsByNumeroControle(pedido.getNumeroControle())){
            throw new PedidoNumeroControleExistsException("Número de controle já existe");
        }
    }

    private void checkDataCadastro(PedidoDTO pedido){
        if(pedido.getDataCadastro() == null){
            String dataStr = LocalDate.now().toString();
            pedido.setDataCadastro(dataStr);
        }
    }

    private void checkNomeProduto(PedidoDTO pedido){
        if(pedido.getNome() == null){
            throw new PedidoNomeProdutoControleException("Nome do produto ausente");
        }
    }

    private void checkQuantidadeProduto(PedidoDTO pedido){
        if(pedido.getQuantidade() == 0){
            pedido.setQuantidade(1);
        }
    }

    private void checkCodigoCliente(PedidoDTO pedido){
        if(pedido.getCodigoCliente() == 0){
            throw new PedidoCodigoClienteException("Código do cliente ausente");
        }
    }

    public PedidoModel create(PedidoModel pedido) {
        logger.info("Executando o método create");
        return pedidosRepository.save(pedido);

    }

}
