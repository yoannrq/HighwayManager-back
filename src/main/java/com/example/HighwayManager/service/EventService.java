package com.example.HighwayManager.service;

import com.example.HighwayManager.model.Event;
import com.example.HighwayManager.repository.EventRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> getEventById(final long id) {
        return eventRepository.findById(id);
    }

    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(final long id) {
        eventRepository.deleteById(id);
    }

    public Event saveEvent(final Event event) {
        return eventRepository.save(event);
    }
}
