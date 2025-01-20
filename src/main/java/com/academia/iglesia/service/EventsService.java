package com.academia.iglesia.service;

import com.academia.iglesia.model.Events;
import com.academia.iglesia.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
  /*  public List<Events> get30() {

        List<Events> eventsList= eventsRepository.findAll();
        List<Events> eventsList30= new ArrayList<>();
        for (Events event : eventsList) {
            LocalDate today = LocalDate.now();
            LocalDate next30Days = today.plusDays(60);
            if ((today.isEqual(event.getFecha_inicio()) || today.isBefore(event.getFecha_inicio())) &&
                    (event.getFecha_inicio().isEqual(next30Days) || event.getFecha_inicio().isBefore(next30Days))) {
                eventsList30.add(event);
            }
        }
        return eventsList30;
    }
*/
    @Override
    public List<Events> get30() {
        LocalDate today = LocalDate.now();
        LocalDate next60Days = today.plusDays(60);
        return eventsRepository.findEventsInNext60Days(today, next60Days);
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
