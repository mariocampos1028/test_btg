package com.btg.test.repository;

import com.btg.test.domain.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
}
