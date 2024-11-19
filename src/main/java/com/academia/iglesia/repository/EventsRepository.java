package com.academia.iglesia.repository;

import com.academia.iglesia.model.Events;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends MongoRepository<Events, String> {
}
