package com.example.eventreader.service;

import com.example.eventreader.model.Event;
import com.example.eventreader.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public void save(Event event){
        eventRepository.save(event);
    }
}
