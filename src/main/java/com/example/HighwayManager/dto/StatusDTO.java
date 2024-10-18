package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.Status;
import lombok.Data;

@Data
public class StatusDTO {
    private Long id;
    private String name;

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.name = status.getName();
    }
}
