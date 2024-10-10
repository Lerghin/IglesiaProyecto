package com.academia.iglesia.repository;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Nota;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotaRepository extends MongoRepository<Nota, String> {
    
}
