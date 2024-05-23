package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {
    ClienteModel findByCodigo(Integer codigo);

    @Query(" select c from ClienteModel c left join fetch c.pedidos where c.codigo = :codigo  ")
    ClienteModel findClienteFetchPedidos( @Param("codigo") Integer codigo );
}
