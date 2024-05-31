package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    @Query("SELECT COUNT(p) FROM OrderModel p WHERE p.numeroControle IN :numerosControle")
    long countByControlNumbers(@Param("numerosControle") List<Integer> numerosControle);

    Optional<OrderModel> findByNumeroControle(Integer numeroControle);

    Optional<List<OrderModel>> findByDataPedido(LocalDate dataPedido);

}

