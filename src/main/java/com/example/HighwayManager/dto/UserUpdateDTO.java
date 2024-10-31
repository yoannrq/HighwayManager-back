package com.example.HighwayManager.dto;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserUpdateDTO {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstname;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastname;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @Positive(message = "Role ID must be a positive number")
    private Long roleId;
}