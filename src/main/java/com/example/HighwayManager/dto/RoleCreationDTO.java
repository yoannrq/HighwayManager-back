package com.example.HighwayManager.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RoleCreationDTO {
    @NotBlank(message = "Role name cannot be empty")
    @Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")
    private String name;
}
