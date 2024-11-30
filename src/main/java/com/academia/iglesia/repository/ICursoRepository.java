package com.academia.iglesia.repository;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends MongoRepository<Curso, String> {

}
