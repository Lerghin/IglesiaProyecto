package com.academia.iglesia.repository;

import com.academia.iglesia.model.Events;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventsRepository extends MongoRepository<Events, String> {
    @Query("{ 'fecha_inicio' : { $gte: ?0, $lte: ?1 } }")
    List<Events> findEventsInNext60Days(LocalDate today, LocalDate next60Days);

}
