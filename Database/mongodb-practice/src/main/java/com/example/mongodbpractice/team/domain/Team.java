package com.example.mongodbpractice.team.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "team")
public class Team {

    @Transient
    public static final String SEQUENCE_NAME = "team_sequence";

    @Id
    private Long id;

    private String teamName;

    private List<Long> userIds;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @Version
    private Long version;

    public Team() {
    }

    private Team(Long id, String teamName, List<Long> userIds) {
        this.id = id;
        this.teamName = teamName;
        this.userIds = userIds;
    }

    public static Team of(Long id, String teamName, List<Long> userIds) {
        return new Team(id, teamName, userIds);
    }

    public void modifyTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void addUser(Long userId) {
        userIds.add(userId);
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }
}
