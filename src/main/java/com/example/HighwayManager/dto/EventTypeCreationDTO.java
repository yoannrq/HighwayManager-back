package com.example.HighwayManager.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class EventTypeCreationDTO {
    @NotBlank(message = "Le nom du type d'événement ne peut pas être vide")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;
}
