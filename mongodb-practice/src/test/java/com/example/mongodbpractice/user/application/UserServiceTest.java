package com.example.mongodbpractice.user.application;

import com.example.mongodbpractice.utils.MongoDBContainerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserService 통합 테스트")
@SpringBootTest
class UserServiceTest extends MongoDBContainerTest {

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

    @DisplayName("유저 생성 테스트")
    @Test
    void createUser2() {
        // given : 선행조건 기술
        String name = "name";
        int age = 10;
        UserDto userDto = createUserDto(name, age);

        // when : 기능 수행
        UserDto savedUser = userService.createUser(userDto);

        // then : 결과 확인
        System.out.println("22222222222222222222222222222222");
        System.out.println("savedUser.getId() = " + savedUser.getId());
        System.out.println("22222222222222222222222222222222");
    }

    @DisplayName("유저 생성 테스트")
    @Test
    void createUser3() {
        // given : 선행조건 기술
        String name = "name";
        int age = 10;
        UserDto userDto = createUserDto(name, age);

        // when : 기능 수행
        UserDto savedUser = userService.createUser(userDto);

        // then : 결과 확인
        System.out.println("333333333333333333333333333333333333");
        System.out.println("savedUser.getId() = " + savedUser.getId());
        System.out.println("333333333333333333333333333333333333");
    }

    @DisplayName("유저 생성 테스트")
    @Test
    void createUser4() {
        // given : 선행조건 기술
        String name = "name";
        int age = 10;
        UserDto userDto = createUserDto(name, age);

        // when : 기능 수행
        UserDto savedUser = userService.createUser(userDto);

        // then : 결과 확인
        System.out.println("44444444444444444444444444444444");
        System.out.println("savedUser.getId() = " + savedUser.getId());
        System.out.println("44444444444444444444444444444444");
    }

    // @DisplayName("유저 조회 테스트")
    // @Test
    // void getUser() {
    //     // given : 선행조건 기술
    //     String name = "김지수";
    //     int age = 10;
    //     UserDto userDto = createUserDto(name, age);
    //     UserDto savedUser = userService.createUser(userDto);
    //
    //     // when : 기능 수행
    //     UserDto foundUser = userService.getUser(savedUser.getId());
    //
    //     // then : 결과 확인
    //     assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
    //     assertThat(foundUser.getName()).isEqualTo(savedUser.getName());
    //     assertThat(foundUser.getAge()).isEqualTo(savedUser.getAge());
    // }

    private UserDto createUserDto(String name, int age) {
        return new UserDto(0, name, age);
    }
}