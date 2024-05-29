package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.ClientModel;
import com.gerenciador.pedidos.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ClientService {

    private Logger logger = Logger.getLogger(ClientService.class.getName());

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientModel findByCodigo(Integer codigo){
        return clientRepository.findByCodigo(codigo);
    }

    public ClientModel findClienteFetchPedidos(Integer codigo){
        return clientRepository.findClienteFetchPedidos(codigo);
    }

    public ClientModel create(ClientModel cliente) {
        return clientRepository.save(cliente);
    }

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public List<ClientModel> saveAll(List<ClientModel> clientes) {
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
