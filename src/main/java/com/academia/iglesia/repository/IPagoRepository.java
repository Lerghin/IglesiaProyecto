package com.academia.iglesia.repository;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPagoRepository extends MongoRepository<Pago, String> {
}
