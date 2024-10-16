package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.EventType;
import com.example.HighwayManager.service.EventTypeService;
import com.example.HighwayManager.exception.IllegalStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param eventType as an object event type
     * @return The event type object saved
     * @throws IllegalStateException if event type name is already used
     */
    @PostMapping("/event-type")
    public EventType createEventType(@RequestBody EventType eventType) {
        //Verify if event type name is not already in database
        Optional<EventType> existingEventType = eventTypeService.getEventTypeByName(eventType.getName());
        if (existingEventType.isPresent()) {
            throw new IllegalStateException("Ce nom de type d'évènement existe déjà");
        }

        return eventTypeService.saveEventType(eventType);
    }

    /**
     * Read - Get one event type
     * @param id The id of the event type
     * @return eventType || null
     */
    @GetMapping("/event-type/{id}")
    public EventType getEventTypeById(@PathVariable long id) {
        Optional<EventType> eventType = eventTypeService.getEventTypeById(id);
        return eventType.orElse(null);
    }

    /**
     * Read - Get all event types
     * @return - An iterable object of event types
     */
    @GetMapping("/event-type")
    public Iterable<EventType> getEventTypes() {
        return eventTypeService.getAllEventTypes();
    }

    /**
     * Patch - Update an existing event type
     * @param id - The id of the event type to update
     * @param eventTypeBody - The event type object to updated
     * @return eventType || null - the event type object updated
     * @throws IllegalStateException if event type name is already used
     */
    @PatchMapping("/event-type/{id}")
    public EventType updateEventType(@PathVariable long id, @RequestBody EventType eventTypeBody) {
        Optional<EventType> eventTypeInDatabase = eventTypeService.getEventTypeById(id);
        if (eventTypeInDatabase.isPresent()) {
            EventType eventTypeToUpdate = eventTypeInDatabase.get();

            String eventTypeName = eventTypeBody.getName();
            if (eventTypeName != null && !eventTypeName.isEmpty()) {
                eventTypeToUpdate.setName(eventTypeName);
            }

            return eventTypeService.saveEventType(eventTypeToUpdate);
        } else {
            return null;
        }
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
