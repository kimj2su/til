package com.example.mongodbpractice.team.application;

import com.example.mongodbpractice.sequence.SequenceGeneratorService;
import com.example.mongodbpractice.team.domain.Team;
import com.example.mongodbpractice.team.domain.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public TeamService(TeamRepository teamRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.teamRepository = teamRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public TeamDto createTeam(TeamDto teamDto) {
        long id = sequenceGeneratorService.generateSequence(Team.SEQUENCE_NAME);
        return TeamDto.from(teamRepository.save(teamDto.toEntity(id)));
    }

    public TeamDto findTeam(Long id) {
        return TeamDto.from(teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다.")));
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    public TeamDto modifyTeamName(Long id, String teamName) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        team.modifyTeamName(teamName);
        return TeamDto.from(teamRepository.save(team));
    }

    public TeamDto addUser(Long id, Long userId) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        team.addUser(userId);
        return TeamDto.from(teamRepository.save(team));
    }
}
