package com.example.HighwayManager.dto;
import lombok.Data;

@Data
public class UserCreationDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Long roleId;
}
