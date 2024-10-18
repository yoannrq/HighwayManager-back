package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.Team;
import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String teamName;
    private Long masterId;
    private String masterEmail;

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        if (team.getMaster() != null) {
            this.masterId = team.getMaster().getId();
            this.masterEmail = team.getMaster().getEmail();
        }
    }
}
