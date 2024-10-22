package com.example.HighwayManager.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class TeamCreationDTO {
    @NotBlank(message = "Team name cannot be empty")
    @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
    private String teamName;

    @NotNull(message = "Master ID cannot be null")
    @Positive(message = "Master ID must be a positive number")
    private Long masterId;
}
