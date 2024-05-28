package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

}

