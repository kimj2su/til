package com.example.mongodbpractice.team.application;

import com.example.mongodbpractice.user.application.UserDto;
import com.example.mongodbpractice.user.application.UserService;
import com.example.mongodbpractice.utils.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TeamService 통합 테스트")
class TeamServiceTest extends AcceptanceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    private UserDto userDto;
    @BeforeEach
    void createUser() {
        String name = "name";
        int age = 10;
        userDto = createUserDto(name, age);
        userDto = userService.createUser(userDto);
    }

    @DisplayName("팀 생성 테스트")
    @Test
    void createTeam() {
        // given : 선행조건 기술
        TeamDto teamDto = createTeamDto("team", userDto);

        // when : 기능 수행
        TeamDto savedTeam = teamService.createTeam(teamDto);

        // then : 결과 확인
        assertThat(savedTeam.id()).isEqualTo(1L);
        assertThat(savedTeam.teamName()).isEqualTo("team");
        assertThat(savedTeam.userIds()).isEqualTo(List.of(userDto.getId()));
        assertThat(savedTeam.createdDate()).isNotNull();
    }

    @DisplayName("팀 조회 테스트")
    @Test
    void findTeam() {
        // given : 선행조건 기술
        TeamDto teamDto = createTeamDto("team", userDto);
        TeamDto savedTeam = teamService.createTeam(teamDto);

        // when : 기능 수행
        TeamDto findTeam = teamService.findTeam(savedTeam.id());

        // then : 결과 확인
        assertThat(findTeam.id()).isEqualTo(1L);
        assertThat(findTeam.teamName()).isEqualTo("team");
        assertThat(findTeam.userIds()).isEqualTo(List.of(userDto.getId()));
    }

    @DisplayName("팀 수정 테스트")
    @Test
    void modifyTeam() {
        // given : 선행조건 기술
        TeamDto teamDto = createTeamDto("team", userDto);
        TeamDto savedTeam = teamService.createTeam(teamDto);
        String modifyTeamName = "modifyTeam";

        // when : 기능 수행
        TeamDto modifyTeam = teamService.modifyTeamName(savedTeam.id(), modifyTeamName);

        // then : 결과 확인
        assertThat(modifyTeam.id()).isEqualTo(1L);
        assertThat(modifyTeam.teamName()).isEqualTo(modifyTeamName);
        assertThat(modifyTeam.userIds()).isEqualTo(List.of(userDto.getId()));
    }

    @DisplayName("팀 삭제 테스트")
    @Test
    void deleteTeam() {
        // given : 선행조건 기술
        TeamDto teamDto = createTeamDto("team", userDto);
        TeamDto savedTeam = teamService.createTeam(teamDto);

        // when : 기능 수행
        teamService.deleteTeam(savedTeam.id());

        // then : 결과 확인
        assertThatThrownBy(() -> teamService.findTeam(savedTeam.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 팀입니다.");
    }

    @DisplayName("팀에 유저 추가 테스트")
    @Test
    void addUser() {
        // given : 선행조건 기술
        TeamDto teamDto = createTeamDto("team", userDto);
        TeamDto savedTeam = teamService.createTeam(teamDto);
        UserDto userDto2 = createUserDto("name2", 20);
        userDto2 = userService.createUser(userDto2);

        // when : 기능 수행
        TeamDto modifyTeam = teamService.addUser(savedTeam.id(), userDto2.getId());

        // then : 결과 확인
        assertThat(modifyTeam.id()).isEqualTo(1L);
        assertThat(modifyTeam.teamName()).isEqualTo("team");
        assertThat(modifyTeam.userIds()).isEqualTo(List.of(userDto.getId(), userDto2.getId()));
    }

    private TeamDto createTeamDto(String teamName, UserDto userDto) {
        return TeamDto.of(1L, teamName, List.of(userDto.getId()));
    }

    private UserDto createUserDto(String name, int age) {
        return new UserDto(1L, name, age);
    }
}