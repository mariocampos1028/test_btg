package com.btg.test.repository;

import com.btg.test.domain.Cliente;
import com.btg.test.domain.Fondo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FondoRepository extends MongoRepository<Fondo, Integer> {
}
