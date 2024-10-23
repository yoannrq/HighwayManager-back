package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.EventType;
import lombok.Data;

@Data
public class EventTypeDTO {
    private Long id;
    private String name;

    public EventTypeDTO(EventType eventType) {
        this.id = eventType.getId();
        this.name = eventType.getName();
    }
}
