package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto,Integer> {
}
