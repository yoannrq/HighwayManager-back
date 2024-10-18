package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.TeamCreationDTO;
import com.example.HighwayManager.dto.TeamDTO;
import com.example.HighwayManager.model.Team;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.TeamService;
import com.example.HighwayManager.util.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.HighwayManager.exception.IllegalStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;
    private final EntityValidator entityValidator;

    @Autowired
    public TeamController(TeamService teamService, EntityValidator entityValidator) {
        this.teamService = teamService;
        this.entityValidator = entityValidator;
    }

    /**
     * Create - Add a new team
     * @param teamBody as an object team
     * @return The team object saved
     * @throws  IllegalStateException if team name is already used
     */
    @PostMapping("/team")
    public TeamDTO createTeam(@RequestBody TeamCreationDTO teamBody) {
        //Verify if name is not already in database
        Optional<Team> existingTeam = teamService.getTeamByName(teamBody.getTeamName());
        if (existingTeam.isPresent()) {
            throw new IllegalStateException("Ce nom d'équipe est déjà utilisé");
        }

        //Verify if the master is existing
        User master = entityValidator.validateAndGetUser(teamBody.getMasterId());

        Team newTeam = new Team();
        newTeam.setTeamName(teamBody.getTeamName());
        newTeam.setMaster(master);

        Team savedTeam = teamService.saveTeam(newTeam);
        return new TeamDTO(savedTeam);
    }

    /**
     * Read - Get one team by id
     * @param id - The id of the team
     * @return A TeamDTO object
     * @throws IllegalArgumentException if team is not found
     */
    @GetMapping("/team/{id}")
    public TeamDTO getTeamById(@PathVariable int id) {
        Optional<Team> optionalTeam = teamService.getTeamById(id);
        if (optionalTeam.isPresent()) {
            return new TeamDTO(optionalTeam.get());
        } else {
            throw new IllegalArgumentException("Équipe non trouvée");
        }
    }


    /**
     * Read - Get all teams
     * @return - List of teams
     */
    @GetMapping("/team")
    public List<TeamDTO> getAllTeams() {
        Iterable<Team> teams = teamService.getAllTeams();
        List<TeamDTO> teamDTOs = new ArrayList<>();
        for (Team team : teams) {
            teamDTOs.add(new TeamDTO(team));
        }
        return teamDTOs;
    }

    /**
     * Patch - Update an existing team
     * @param id - The id of the team to update
     * @param teamBody - The team object to update
     * @return team || null - The team object updated
     * @throws IllegalArgumentException if team is not found
     * @throws IllegalStateException if team name is already used
     */
    @PatchMapping("/team/{id}")
    public TeamDTO updateTeam(@PathVariable int id, @RequestBody TeamCreationDTO teamBody) {
        Optional<Team> teamInDatabase = teamService.getTeamById(id);

        if (teamInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Équipe non trouvée");
        }

        Team teamToUpdate = teamInDatabase.get();

        String teamName = teamBody.getTeamName();
        if (teamName != null && !teamName.isEmpty()) {
            //Verify if team name is not already used in database
            Optional<Team> isTeamNameAlreadyUsed = teamService.getTeamByName(teamName);
            if (isTeamNameAlreadyUsed.isEmpty()) {
                teamToUpdate.setTeamName(teamName);
            } else {
                throw new IllegalStateException("Ce nom d'équipe est déjà utilisé");
            }
        }

        if (teamBody.getMasterId() != null) {
            // Verify is the master is existing
            User master = entityValidator.validateAndGetUser(teamBody.getMasterId());
            teamToUpdate.setMaster(master);
        }

        Team updatedTeam = teamService.saveTeam(teamToUpdate);
        return new TeamDTO(updatedTeam);
    }

    /**
     * Delete - Delete a team
     * @param id - The id of the team to delete
     */
    @DeleteMapping("/team/{id}")
    public void deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
    }
}
