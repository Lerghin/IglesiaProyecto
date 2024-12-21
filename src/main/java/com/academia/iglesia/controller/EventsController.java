package com.academia.iglesia.controller;

import com.academia.iglesia.model.Events;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.service.IEventService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app/")
@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private IEventService eventService;

    @GetMapping("/get")
    public List<Events> get() throws  RuntimeException{
        List<Events> eventsList= eventService.get();
        if(eventsList.isEmpty()){
            throw new RuntimeException("No events found");
        }

        return eventsList;
    }
    @PostMapping("/create")
    public String createMiembro(@RequestBody Events events){
        eventService.save(events);
        return "Creado correctamente";
    }

    @DeleteMapping("/delete/{idEvents}")
    public String delete(@PathVariable String idEvents ) throws RuntimeException  {
       eventService.delete(idEvents);
        return "Borrado Correctamente";

    }


    @GetMapping("/get/{idEvents}")
    public Events find(@PathVariable String idEvents) throws  RuntimeException{
        Events events= eventService.find(idEvents);
        if (events == null) {
            throw new RuntimeException("Member with ID " + idEvents + " not found");
        }
        return events;
    }

    @PutMapping("/{idEvents}")
    public Events editEvents(@PathVariable String idEvents, @RequestBody Events events) throws  RuntimeException{
        Events existingEvents = this.find(idEvents);
        if (existingEvents == null) {
            throw new RuntimeException("Member with ID " + idEvents + " not found");
        }
        Events eventsEdited = eventService.edit(idEvents,events);
        return eventsEdited;

    }



}
