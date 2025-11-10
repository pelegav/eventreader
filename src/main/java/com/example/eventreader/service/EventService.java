package com.example.eventreader.service;

import com.example.eventreader.model.Event;
import com.example.eventreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void save(Event event){
        eventRepository.save(event);
    }
}
