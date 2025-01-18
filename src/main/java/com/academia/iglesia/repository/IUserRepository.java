package com.academia.iglesia.repository;


import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

     Optional<User> findByUsername(String username);

     boolean existsByCedula(Integer cedula);


     boolean existsByUsername(String username);
}
