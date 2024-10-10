package com.academia.iglesia.repository;

import com.academia.iglesia.model.Miembro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembrosRepository extends MongoRepository<Miembro, String> {
     Miembro findByCedula(String cedula);

}
