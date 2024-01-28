package com.example.mongodbpractice.team.application;

import com.example.mongodbpractice.team.domain.Team;

import java.util.Date;
import java.util.List;

public record TeamDto (
        Long id,
        String teamName,
        List<Long> userIds,
        Date createdDate,
        Date lastModifiedDate
) {

    public Team toEntity(Long id) {
        return Team.of(id, teamName, userIds);
    }

    public static TeamDto of(Long id, String teamName, List<Long> userIds) {
        return new TeamDto(id, teamName, userIds, null, null);
    }
    public static TeamDto from(Team team) {
        return new TeamDto(
                team.getId(),
                team.getTeamName(),
                team.getUserIds(),
                team.getCreatedDate(),
                team.getLastModifiedDate()
        );
    }
}
