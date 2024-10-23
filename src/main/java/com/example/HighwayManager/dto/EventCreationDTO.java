package com.example.HighwayManager.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventCreationDTO {
    @NotBlank(message = "Event date cannot be empty")
    private LocalDate date;

    @NotNull(message = "Team ID cannot be null")
    @Positive(message = "Team ID must be a positive number")
    private Long teamId;

    @NotNull(message = "Event Type ID cannot be null")
    @Positive(message = "Event Type ID must be a positive number")
    private Long eventTypeId;

    @NotNull(message = "Status ID cannot be null")
    @Positive(message = "Status ID must be a positive number")
    private Long statusId;
}
