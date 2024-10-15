package com.btg.test.repository;

import com.btg.test.domain.IncripcionesFondoCliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface IncripcionFondoClienteRepository extends MongoRepository<IncripcionesFondoCliente, Integer> {

    @Query("{'_id': ?0, 'id_cliente': ?1}")
    Optional<IncripcionesFondoCliente> findByClienteAndFondo(Integer idCliente, Long idFondo);

}
