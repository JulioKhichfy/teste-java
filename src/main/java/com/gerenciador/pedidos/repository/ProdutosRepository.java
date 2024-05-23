package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<ProdutoModel,Integer> {
}
