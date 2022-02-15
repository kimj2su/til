package com.example.mysql_concurrency.jpa;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Data
@Entity
@OptimisticLocking
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String teamName;

    @Builder
    public Team(Member member, String teamName) {
        this.member = member;
        this.teamName = teamName;
    }
}
