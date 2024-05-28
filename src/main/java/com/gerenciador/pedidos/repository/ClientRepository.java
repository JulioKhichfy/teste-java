package com.gerenciador.pedidos.repository;

import com.gerenciador.pedidos.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    ClientModel findByCodigo(Integer codigo);

    @Query(" select c from ClientModel c left join fetch c.pedidos where c.codigo = :codigo  ")
    ClientModel findClienteFetchPedidos(@Param("codigo") Integer codigo );
}
