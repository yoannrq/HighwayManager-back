package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.*;
import com.example.HighwayManager.service.EventService;
import com.example.HighwayManager.util.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final EntityValidator entityValidator;

    @Autowired
    public EventController(EventService eventService, EntityValidator entityValidator) {
        this.eventService = eventService;
        this.entityValidator = entityValidator;
    }

    /**
     * Create - Add a new event
     * @param eventBody as an object event
     * @return The event object saved
     */
    @PostMapping("/event")
    public Event createEvent(@RequestBody Event eventBody) {
        //Verify if the team is existing
        Team bodyTeam = eventBody.getTeam();
        entityValidator.validateTeam(bodyTeam.getId());

        //Verify if the event type is existing
        EventType bodyEventType = eventBody.getType();
        entityValidator.validateEventType(bodyEventType.getId());

        //Verify if the status is existing
        Status bodyStatus = eventBody.getStatus();
        entityValidator.validateStatus(bodyStatus.getId());

        return eventService.saveEvent(eventBody);
    }

    /**
     * Read - Get one event
     * @param id The id of the event
     * @return event || null
     */
    @GetMapping("/event/{id}")
    public Event getEvent(@PathVariable long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.orElse(null);
    }

    /**
     * Read - Get all events
     * @return - An iterable object of events
     */
    @GetMapping("/event")
    public Iterable<Event> getEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Patch - Update an existing event
     * @param id - The id of the event to update
     * @param eventBody - The event object to update
     * @return event || null - The event object updated
     */
    @PatchMapping("/event/{id}")
    public Event updateEvent(@PathVariable long id, @RequestBody Event eventBody) {
        Optional<Event> eventInDatabase = eventService.getEventById(id);
        if (eventInDatabase.isPresent()) {
            Event eventToUpdate = eventInDatabase.get();

            LocalDate date = eventBody.getDate();
            if (date != null) {
                eventToUpdate.setDate(date);
            }

            Team teamBody = eventBody.getTeam();
            if (teamBody != null && teamBody.getId() != null) {
                //Verify if the team is existing
                entityValidator.validateTeam(teamBody.getId());
                eventToUpdate.setTeam(teamBody);
            }

            EventType eventTypeBody = eventBody.getType();
            if (eventTypeBody != null && eventTypeBody.getId() != null) {
                //Verify if the event type is existing
                entityValidator.validateEventType(eventTypeBody.getId());
                eventToUpdate.setType(eventTypeBody);
            }

            Status statusBody = eventBody.getStatus();
            if (statusBody != null && statusBody.getId() != null) {
                //Verify if the status is existing
                entityValidator.validateStatus(statusBody.getId());
                eventToUpdate.setStatus(statusBody);
            }

            return eventService.saveEvent(eventToUpdate);
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an event
     * @param id - The id of the event to delete
     */
    @DeleteMapping("/event/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }
}
