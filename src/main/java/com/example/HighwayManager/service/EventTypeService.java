package com.example.HighwayManager.service;

import com.example.HighwayManager.model.EventType;
import com.example.HighwayManager.repository.EventTypeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class EventTypeService {
    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public Optional<EventType> getEventTypeById(long id) {
        return eventTypeRepository.findById(id);
    }

    public Optional<EventType> getEventTypeByName(String name) {
        return eventTypeRepository.findByName(name);
    }

    public Iterable<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public void deleteEventType(long id) {
        eventTypeRepository.deleteById(id);
    }

    public EventType saveEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }
}
