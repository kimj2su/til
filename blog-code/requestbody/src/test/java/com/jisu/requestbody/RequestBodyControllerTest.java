package com.jisu.requestbody;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestBodyControllerTest {

    @LocalServerPort
    int port;

    @Test
    void getTime() {
        // given : 선행조건 기술
        RequestBodyDto requestBodyDto = new RequestBodyDto();
        requestBodyDto.setFiled(System.currentTimeMillis());
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String uri = "http://localhost:" + port + "/times";

        // when : 기능 수행
        RequestBodyDto response = testRestTemplate.postForObject(uri, requestBodyDto, RequestBodyDto.class);

        // then : 결과 확인
        System.out.println("response = " + response);
        assertThat(response.getFiled()).isNotNull();
    }

}