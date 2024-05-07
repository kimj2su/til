package com.jisu.testcodewitharchitecture.user.service;

import com.jisu.testcodewitharchitecture.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        // given : 선행조건 기술
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when : 기능 수행
        certificationService.send("kimjisu3268@gmail.com", 1, "aaaaa-aaaaa-aaaaa-aaaaa");

        // then : 결과 확인
        assertThat(fakeMailSender.getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(fakeMailSender.getTitle()).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.getContent()).contains("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaa-aaaaa-aaaaa-aaaaa");
    }
}
