package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    public ClientModel findByCodigo(Integer codigo) {
        return clientRepository.findByCodigo(codigo);
    }

    public ClientModel findClienteFetchPedidos(Integer codigo) {
        return clientRepository.findClienteFetchPedidos(codigo);
    }

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public List<ClientModel> saveAll(List<ClientModel> clientes) {
        logger.info("Salvando clientes e seus pedidos");
        return clientRepository.saveAll(clientes);
    }

    public void saveOrUpdate(List<ClientModel> clients) {
        List<ClientModel> clientsTOSaveOrUpdate = new ArrayList<>();
        for(ClientModel clientModel : clients) {
            ClientModel clientBD = clientRepository.findByCodigo(clientModel.getCodigo());
            if(clientBD != null){
                clientModel.setId(clientBD.getId());
            }
            clientsTOSaveOrUpdate.add(clientModel);
        }
        saveAll(clients);
    }
}
