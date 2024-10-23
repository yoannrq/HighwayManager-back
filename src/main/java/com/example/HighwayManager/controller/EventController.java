package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.EventCreationDTO;
import com.example.HighwayManager.dto.EventDTO;
import com.example.HighwayManager.model.*;
import com.example.HighwayManager.service.EventService;
import com.example.HighwayManager.exception.IllegalArgumentException;
import com.example.HighwayManager.service.EventTypeService;
import com.example.HighwayManager.service.StatusService;
import com.example.HighwayManager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final TeamService teamService;
    private final EventTypeService eventTypeService;
    private final StatusService statusService;

    @Autowired
    public EventController(EventService eventService, TeamService teamService, EventTypeService eventTypeService, StatusService statusService) {
        this.eventService = eventService;
        this.teamService = teamService;
        this.eventTypeService = eventTypeService;
        this.statusService = statusService;
    }

    /**
     * Create - Add a new event
     * @param eventBody as an object event
     * @return The event object saved
     * @throws IllegalArgumentException if status, team or event type is not found
     */
    @PostMapping("/event")
    public EventDTO createEvent(@Valid @RequestBody EventCreationDTO eventBody) {
        Event newEvent = new Event();

        //Verify if team is existing
        Team team = teamService.getTeamById(eventBody.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));
        newEvent.setTeam(team);

        //Verify if event type is existing
        EventType eventType = eventTypeService.getEventTypeById(eventBody.getEventTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Type d'évènement introuvable"));
        newEvent.setType(eventType);

        //Verify if status is existing
        Status status = statusService.getStatusById(eventBody.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Statut introuvable"));
        newEvent.setStatus(status);

        Event savedEvent = eventService.saveEvent(newEvent);
        return new EventDTO(savedEvent);
    }

    /**
     * Read - Get one event
     * @param id The id of the event
     * @return event
     * @throws IllegalArgumentException if event is not found
     */
    @GetMapping("/event/{id}")
    public EventDTO getEvent(@PathVariable final long id) {
        Optional<Event> optionalEvent = eventService.getEventById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            return new EventDTO(event);
        } else {
            throw new IllegalArgumentException("Évènement introuvable");
        }
    }

    /**
     * Read - Get all events
     * @return - List of events
     */
    @GetMapping("/event")
    public List<EventDTO> getEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event event : events) {
            eventDTOs.add(new EventDTO(event));
        }
        return eventDTOs;
    }

    /**
     * Patch - Update an existing event
     * @param id - The id of the event to update
     * @param eventBody - The event object to update
     * @return event - The event object updated
     * @throws IllegalArgumentException if team, event type or status is not found
     */
    @PatchMapping("/event/{id}")
    public EventDTO updateEvent(@PathVariable long id, @Valid @RequestBody EventCreationDTO eventBody) {
        Optional<Event> eventInDatabase = eventService.getEventById(id);

        if (eventInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Évènement introuvable");
        }

        Event eventToUpdate = eventInDatabase.get();

        if (eventBody.getDate() != null) {
            eventToUpdate.setDate(eventBody.getDate());
        }

        if (eventBody.getTeamId() != null) {
            Team team = teamService.getTeamById(eventBody.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));
            eventToUpdate.setTeam(team);
        }

        if (eventBody.getEventTypeId() != null) {
            EventType eventType = eventTypeService.getEventTypeById(eventBody.getEventTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Type d'évènement introuvable"));
            eventToUpdate.setType(eventType);
        }

        if (eventBody.getStatusId() != null) {
            Status status = statusService.getStatusById(eventBody.getStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("Statut introuvable"));
            eventToUpdate.setStatus(status);
        }

        Event savedEvent = eventService.saveEvent(eventToUpdate);
        return new EventDTO(savedEvent);
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
