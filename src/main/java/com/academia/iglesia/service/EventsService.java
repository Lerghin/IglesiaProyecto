package com.academia.iglesia.service;

import com.academia.iglesia.model.Events;
import com.academia.iglesia.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService implements  IEventService {
    @Autowired
    private EventsRepository eventsRepository;

    @Override
    public List<Events> get() {

        List<Events> eventsList= eventsRepository.findAll();
        return eventsList;
    }

    @Override
    public void save(Events events) {
       eventsRepository.save(events);

    }

    @Override
    public void delete(String idEvents) {
       eventsRepository.deleteById(idEvents);
    }

    @Override
    public Events find(String idEvents) {
        Events events= eventsRepository.findById(idEvents).orElseThrow();

        return events;
    }

    @Override
    public Events edit(String idEvents, Events events) {
        Events eventsFound= this.find(idEvents);
        eventsFound.setNameEvents(events.getNameEvents());
        eventsFound.setFecha_inicio(events.getFecha_inicio());
        eventsFound.setDescription(events.getDescription());
        eventsRepository.save(eventsFound);
        return eventsFound;
    }
}
