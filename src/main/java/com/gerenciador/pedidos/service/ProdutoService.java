package com.gerenciador.pedidos.service;

import com.gerenciador.pedidos.model.ProdutoModel;
import com.gerenciador.pedidos.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ProdutoService {
    private Logger logger = Logger.getLogger(ProdutoService.class.getName());

    @Autowired
    private ProdutosRepository repository;

    public ProdutoModel create(ProdutoModel produto) {
        logger.info("Executando o método create produto");
        return repository.save(produto);
    }
}
