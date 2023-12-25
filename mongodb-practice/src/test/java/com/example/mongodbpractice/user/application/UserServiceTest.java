package com.example.mongodbpractice.user.application;

import com.example.mongodbpractice.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserService 통합 테스트")
class UserServiceTest extends AcceptanceTest {

    @Autowired
    private UserService userService;

    @DisplayName("유저 생성 테스트")
    @Test
    void createUser() {
        // given : 선행조건 기술
        String name = "name";
        int age = 10;
        UserDto userDto = createUserDto(name, age);

        // when : 기능 수행
        UserDto savedUser = userService.createUser(userDto);

        // then : 결과 확인
        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getName()).isEqualTo("name");
        assertThat(savedUser.getAge()).isEqualTo(10);
    }

    @DisplayName("유저 조회 테스트")
    @Test
    void getUser() {
        // given : 선행조건 기술
        String name = "김지수";
        int age = 10;
        UserDto userDto = createUserDto(name, age);
        UserDto savedUser = userService.createUser(userDto);

        // when : 기능 수행
        UserDto foundUser = userService.getUser(savedUser.getId());

        // then : 결과 확인
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getName()).isEqualTo(savedUser.getName());
        assertThat(foundUser.getAge()).isEqualTo(savedUser.getAge());
    }

    @DisplayName("유저 수정 테스트")
    @Test
    void modify() {
        // given : 선행조건 기술
        String name = "김지수";
        int age = 10;
        UserDto userDto = createUserDto(name, age);
        UserDto savedUser = userService.createUser(userDto);
        String modifyUserName = "김지수2";
        int modifyUserAge = 20;

        // when : 기능 수행
        UserDto modifiedUser = userService.modifyUser(savedUser.getId(), createUserDto(modifyUserName, modifyUserAge));

        // then : 결과 확인
        assertThat(savedUser.getId()).isEqualTo(modifiedUser.getId());
        assertThat(modifyUserName).isEqualTo(modifiedUser.getName());
        assertThat(modifyUserAge).isEqualTo(modifiedUser.getAge());
    }

    @DisplayName("유저 삭제 테스트")
    @Test
    void delete() {
        // given : 선행조건 기술
        String name = "김지수";
        int age = 10;
        UserDto userDto = createUserDto(name, age);
        UserDto savedUser = userService.createUser(userDto);

        // when : 기능 수행
        userService.deleteUser(savedUser.getId());

        // then : 결과 확인
        assertThatThrownBy(() -> userService.getUser(savedUser.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 유저가 없습니다.");
    }

    private UserDto createUserDto(String name, int age) {
        return new UserDto(0, name, age);
    }
}