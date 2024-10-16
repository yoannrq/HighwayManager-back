package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.Team;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.TeamService;
import com.example.HighwayManager.util.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.HighwayManager.exception.IllegalStateException;

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
    public Team createTeam(@RequestBody Team teamBody) {
        //Verify if name is not already in database
        Optional<Team> existingTeam = teamService.getTeamByName(teamBody.getTeamName());
        if (existingTeam.isPresent()) {
            throw new IllegalStateException("Ce nom d'équipe est déjà utilisé");
        }

        //Verify if the master is existing
        User bodyMaster = teamBody.getMaster();
        entityValidator.validateUser(bodyMaster.getId());

        return teamService.saveTeam(teamBody);
    }

    /**
     * Read - Get one team by id
     * @param id - The id of the team
     * @return A team object
     */
    @GetMapping("/team/{id}")
    public Team getTeamById(@PathVariable int id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.orElse(null);
    }

    /**
     * Read - Get all teams
     * @return - An iterable object of teams
     */
    @GetMapping("/team")
    public Iterable<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    /**
     * Patch - Update an existing team
     * @param id - The id of the team to update
     * @param teamBody - The team object to update
     * @return team || null - The team object updated
     * @throws IllegalStateException if team name is already used
     */
    @PatchMapping("/team/{id}")
    public Team updateTeam(@PathVariable int id, @RequestBody Team teamBody) {
        Optional<Team> teamInDatabase = teamService.getTeamById(id);
        if (teamInDatabase.isPresent()) {
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

            User userBody = teamBody.getMaster();
            if (userBody != null && userBody.getId() != null) {
                //Verify if the master is existing
                entityValidator.validateUser(userBody.getId());
                teamToUpdate.setMaster(userBody);
            }

            return teamService.saveTeam(teamToUpdate);
        } else {
            return null;
        }
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
