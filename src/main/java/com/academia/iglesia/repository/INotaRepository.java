package com.academia.iglesia.repository;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.model.Nota;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaRepository extends MongoRepository<Nota, String> {
    List<Nota> findByModulo(String idModulo);
    
}
