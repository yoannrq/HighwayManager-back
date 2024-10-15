package com.example.HighwayManager.service;

import com.example.HighwayManager.model.Team;
import com.example.HighwayManager.repository.TeamRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> getTeamById(final long id) {
        return teamRepository.findById(id);
    }

    public Optional<Team> getTeamByName(final String name) {
        return teamRepository.findByTeamName(name);
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void deleteTeam(final long id) {
        teamRepository.deleteById(id);
    }

    public Team saveTeam(final Team team) {
        return teamRepository.save(team);
    }
}
