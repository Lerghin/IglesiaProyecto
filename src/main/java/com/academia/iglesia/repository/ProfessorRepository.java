package com.academia.iglesia.repository;

import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends MongoRepository<Professor, String> {
    Professor findByCedula(String cedula);

}