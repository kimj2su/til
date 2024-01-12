package com.example.mongodbpractice.team.application;

import com.example.mongodbpractice.team.domain.Team;

import java.util.List;

public record TeamDto (Long id, String teamName, List<Long> userIds) {

    public Team toEntity(Long id) {
        return Team.of(id, teamName, userIds);
    }

    public static TeamDto from(Team team) {
        return new TeamDto(team.getId(), team.getTeamName(), team.getUserIds());
    }
}
