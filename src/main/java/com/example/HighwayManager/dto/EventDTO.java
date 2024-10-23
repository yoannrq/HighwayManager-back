package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.Event;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {
    private Long id;
    private LocalDate date;
    private String teamName;
    private String eventTypeName;
    private String statusName;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.date = event.getDate();
        this.teamName = event.getTeam() != null ? event.getTeam().getTeamName() : null;
        this.eventTypeName = event.getType() != null ? event.getType().getName() : null;
        this.statusName = event.getStatus() != null ? event.getStatus().getName() : null;
    }
}
