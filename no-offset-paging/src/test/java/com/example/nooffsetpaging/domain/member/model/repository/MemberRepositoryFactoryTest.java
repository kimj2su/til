package com.example.nooffsetpaging.domain.member.model.repository;

import com.example.nooffsetpaging.domain.member.dto.MemberDto;
import com.example.nooffsetpaging.domain.member.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryFactoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberRepositoryFactory memberRepositoryFactory;

    @Test
    void paginationLegacy() {
        // given : 선행조건 기술
        String prefixName = "a";

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationLegacy(prefixName, 99999, 10);
        for (MemberDto member : members) {
            System.out.println("member = " + member.getName());
        }

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
    }

    @Test
    void paginationNoOffset1() {
        // given : 선행조건 기술
        String prefixName = "a";

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationNoOffsetBuilder(null, prefixName, 10);

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
    }

    @Test
    void paginationNoOffset2() {
        // given : 선행조건 기술
        String prefixName = "a";

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationNoOffset(11L, prefixName, 10);
        for (MemberDto member : members) {
            System.out.println("member.getName() = " + member.getName());
        }
        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
    }
}