package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<ProdutoModel, Integer> {

}

