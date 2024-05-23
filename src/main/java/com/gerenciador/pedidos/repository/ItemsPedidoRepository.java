package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.ItemPedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsPedidoRepository extends JpaRepository<ItemPedidoModel, Integer> {
}
