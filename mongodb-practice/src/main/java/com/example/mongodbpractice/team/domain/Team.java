package com.example.mongodbpractice.team.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "team")
public class Team {

    @Transient
    public static final String SEQUENCE_NAME = "team_sequence";

    @Id
    private long id;

    private String teamName;

    public Team(long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }
}
