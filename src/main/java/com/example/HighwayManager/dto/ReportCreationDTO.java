package com.example.HighwayManager.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ReportCreationDTO {
    @NotNull(message = "Author ID cannot be null")
    @Positive(message = "Author ID must be a positive number")
    private Long authorId;

    @NotNull(message = "Event ID cannot be null")
    @Positive(message = "Event ID must be a positive number")
    private Long eventId;
}
