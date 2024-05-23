package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<PedidoModel, Integer> {
    boolean existsByNumeroControle(Integer numeroControle);
}

