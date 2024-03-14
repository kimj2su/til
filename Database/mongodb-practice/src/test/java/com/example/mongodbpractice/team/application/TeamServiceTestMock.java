package com.example.mongodbpractice.team.application;

import com.example.mongodbpractice.team.domain.Team;
import com.example.mongodbpractice.team.domain.TeamRepository;
import com.example.mongodbpractice.user.application.UserDto;
import com.example.mongodbpractice.user.application.UserService;
import com.example.mongodbpractice.utils.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("TeamService 통합 테스트 목")
@ExtendWith(MockitoExtension.class)
class TeamServiceTestMock extends AcceptanceTest {

    @Autowired
    private TeamService teamService;

    @MockBean
    private TeamRepository teamRepository;
    // @MockBean
    // private UserService userService;
    //
    private UserDto userDto;
    // @BeforeEach
    // void createUser() {
    //     String name = "name";
    //     int age = 10;
    //     userDto = createUserDto(name, age);
    //     userDto = userService.createUser(userDto);
    // }

    @DisplayName("팀 생성 테스트")
    @Test
    void createTeam() {
        // given : 선행조건 기술
        String name = "name";
        int age = 10;
        userDto = createUserDto(name, age);
        TeamDto teamDto = createTeamDto("team", userDto);
        Team entity = teamDto.toEntity(1L);
        BDDMockito.given(teamRepository.save(any())).willReturn(entity);

        // when : 기능 수행
        TeamDto savedTeam = teamService.createTeam(teamDto);

        // then : 결과 확인
        assertThat(savedTeam.id()).isEqualTo(1L);
        assertThat(savedTeam.teamName()).isEqualTo("team");
        assertThat(savedTeam.userIds()).isEqualTo(List.of(userDto.getId()));
    }

    private TeamDto createTeamDto(String teamName, UserDto userDto) {
        return TeamDto.of(1L, teamName, List.of(userDto.getId()));
    }

    private UserDto createUserDto(String name, int age) {
        return new UserDto(1L, name, age);
    }
}