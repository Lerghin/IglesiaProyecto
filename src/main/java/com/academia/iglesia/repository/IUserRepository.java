package com.academia.iglesia.repository;


import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

     User findByUserName(String userName);
     boolean existsByUserName(String userName);


}
