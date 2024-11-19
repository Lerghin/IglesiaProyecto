package com.academia.iglesia.service;


import com.academia.iglesia.model.Events;
import com.academia.iglesia.model.Professor;


import java.util.List;

public interface IEventService {
    public List<Events> get();
    public void save (Events events);
    public void delete(String idEvents);
    public Events find(String  idEvents);
    public Events edit(String idEvents, Events events);


}
