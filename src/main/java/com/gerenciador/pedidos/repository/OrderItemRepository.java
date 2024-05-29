package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, Integer> {

}
