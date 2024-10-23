package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.EventTypeCreationDTO;
import com.example.HighwayManager.dto.EventTypeDTO;
import com.example.HighwayManager.model.EventType;
import com.example.HighwayManager.service.EventTypeService;
import com.example.HighwayManager.exception.IllegalStateException;
import com.example.HighwayManager.exception.IllegalArgumentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    /**
     * Create - Add a new event type
     * @param eventTypeBody as an object event type
     * @return The event type object saved
     * @throws IllegalStateException if event type name is already used
     */
    @PostMapping("/event-type")
    public EventTypeDTO createEventType(@Valid @RequestBody EventTypeCreationDTO eventTypeBody) {
        //Verify if event type name is not already in database
        if (eventTypeService.getEventTypeByName(eventTypeBody.getName()).isPresent()) {
            throw new IllegalStateException("Ce nom de type d'évènement existe déjà");
        }

        EventType newEventType = new EventType();
        newEventType.setName(eventTypeBody.getName());

        EventType savedEventType = eventTypeService.saveEventType(newEventType);
        return new EventTypeDTO(savedEventType);
    }

    /**
     * Read - Get one event type
     * @param id The id of the event type
     * @return eventType
     * @throws IllegalArgumentException if event is not found
     */
    @GetMapping("/event-type/{id}")
    public EventTypeDTO getEventTypeById(@PathVariable long id) {
        Optional<EventType> optionalEventType = eventTypeService.getEventTypeById(id);
        if (optionalEventType.isPresent()) {
            EventType eventType = optionalEventType.get();
            return new EventTypeDTO(eventType);
        } else {
            throw new IllegalArgumentException("Type d'évènement introuvable");
        }
    }

    /**
     * Read - Get all event types
     * @return - List of event types
     */
    @GetMapping("/event-type")
    public List<EventTypeDTO> getEventTypes() {
        Iterable<EventType> eventTypes = eventTypeService.getAllEventTypes();
        List<EventTypeDTO> eventTypesDTOs = new ArrayList<>();
        for (EventType eventType : eventTypes) {
            eventTypesDTOs.add(new EventTypeDTO(eventType));
        }
        return eventTypesDTOs;
    }

    /**
     * Patch - Update an existing event type
     * @param id - The id of the event type to update
     * @param eventTypeBody - The event type object to updated
     * @return eventType || null - the event type object updated
     * @throws IllegalStateException if event type name is already used
     * @throws IllegalArgumentException if event type is not found
     */
    @PatchMapping("/event-type/{id}")
    public EventTypeDTO updateEventType(@PathVariable long id, @Valid @RequestBody EventType eventTypeBody) {
        Optional<EventType> eventTypeInDatabase = eventTypeService.getEventTypeById(id);

        if (eventTypeInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Type d'évènement introuvable");
        }

        EventType eventTypeToUpdate = eventTypeInDatabase.get();

        if (eventTypeBody.getName() != null && !eventTypeBody.getName().isEmpty() && !eventTypeToUpdate.getName().equals(eventTypeBody.getName())) {
            if (eventTypeService.getEventTypeByName(eventTypeBody.getName()).isPresent()) {
                throw new IllegalStateException("Ce nom de type d'évènement est déjà utilisé");
            }
            eventTypeToUpdate.setName(eventTypeBody.getName());
        }

        EventType savedEventType = eventTypeService.saveEventType(eventTypeToUpdate);
        return new EventTypeDTO(savedEventType);
    }

    /**
     * Delete - Delete an event type
     * @param id - The id of the event type to delete
     */
    @DeleteMapping("/event-type/{id}")
    public void deleteEventType(@PathVariable final long id) {
        eventTypeService.deleteEventType(id);
    }
}
