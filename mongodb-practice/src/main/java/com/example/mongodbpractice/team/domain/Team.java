package com.example.mongodbpractice.team.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "team")
public class Team {

    @Transient
    public static final String SEQUENCE_NAME = "team_sequence";

    @Id
    private long id;

    private String teamName;

    private List<Long> userIds;

    public Team(long id, String teamName, List<Long> userIds) {
        this.id = id;
        this.teamName = teamName;
        this.userIds = userIds;
    }

    public long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Long> getUserIds() {
        return userIds;
    }
}
