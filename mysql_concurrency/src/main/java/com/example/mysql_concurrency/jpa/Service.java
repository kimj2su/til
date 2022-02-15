package com.example.mysql_concurrency.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public Member create(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        if (byId.isEmpty()) {
            Member entity = Member.builder()
                    .username("jisu")
                    .build();
          return  memberRepository.save(entity);
        } else {
            return byId.get();
        }
    }

    @Transactional
    public void modify(Long id) {
        Member member = create(id);
        Optional<Member> byId = memberRepository.findById(id);
        if (byId.get().getUsername().equals("jisu")) {
            member.modifyUsername("jisu2");
        }
    }

    @Transactional
    public void createTeam(Long id) {
        Member member = create(id);
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            Team team1 = Team.builder()
                    .teamName("team" + id)
                    .member(member)
                    .build();
            teamRepository.save(team1);
        }
    }
}
