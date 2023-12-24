package com.example.mongodbpractice.team.ui;

import com.example.mongodbpractice.team.application.TeamDto;
import com.example.mongodbpractice.team.application.TeamService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public TeamDto createTeam(TeamDto teamDto) {
        return teamService.createTeam(teamDto);
    }

    @PatchMapping("/{id}")
    public TeamDto modifyTeam(@PathVariable Long id, @RequestBody String teamName) {
        return teamService.modifyTeamName(id, teamName);
    }

    @GetMapping("/{id}")
    public TeamDto findTeam(@PathVariable Long id) {
        return teamService.findTeam(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}
