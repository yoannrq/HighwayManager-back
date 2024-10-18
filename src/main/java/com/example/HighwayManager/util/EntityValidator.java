package com.example.HighwayManager.util;

import com.example.HighwayManager.exception.EntityNotFoundException;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Utility class for validating and updating entities
@Component
@RequiredArgsConstructor
public class EntityValidator {

    private final TeamService teamService;
    private final EventTypeService eventTypeService;
    private final StatusService statusService;
    private final EventService eventService;
    private final UserService userService;

    /**
     * Validates a Team by its ID.
     *
     * @param teamId The ID of the team to validate
     * @throws EntityNotFoundException if the team is not found
     */
    public void validateTeam(Long teamId) {
        teamService.getTeamById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Équipe", teamId));
    }

    /**
     * Validates an EventType by its ID.
     *
     * @param eventTypeId The ID of the event type to validate
     * @throws EntityNotFoundException if the event type is not found
     */
    public void validateEventType(Long eventTypeId) {
        eventTypeService.getEventTypeById(eventTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Type d'événement", eventTypeId));
    }

    /**
     * Validates a Status by its ID.
     *
     * @param statusId The ID of the status to validate
     * @throws EntityNotFoundException if the status is not found
     */
    public void validateStatus(Long statusId) {
        statusService.getStatusById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Statut", statusId));
    }

    /**
     * Validates an Event by its ID.
     *
     * @param eventId The ID of the event to validate
     * @throws EntityNotFoundException if the event is not found
     */
    public void validateEvent(Long eventId) {
        eventService.getEventById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Événement", eventId));
    }

    /**
     * Validates a User by its ID.
     *
     * @param userId The ID of the user to validate
     * @throws EntityNotFoundException if the user is not found
     */
    public void validateUser(Long userId) {
        userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur", userId));
    }

    /**
     * Validates and return a User by its ID.
     *
     * @param userId The ID of the user to validate
     * @return User the user object
     * @throws EntityNotFoundException if the user is not found
     */
    public User validateAndGetUser(Long userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur", userId));
    }


}
