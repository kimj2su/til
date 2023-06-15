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
//        for (int i = 1; i <= 30; i++) {
//            memberRepository.save(Member.builder()
//                    .name(prefixName + i)
//                    .build());
//        }

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationLegacy(prefixName, 1000, 10);

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
//        assertThat(members.get(0).getName()).isEqualTo("a20");
//        assertThat(members.get(9).getName()).isEqualTo("a11");
    }

    @Test
    void paginationNoOffset1() {
        // given : 선행조건 기술
        String prefixName = "a";
        for (int i = 1; i <= 30; i++) {
            memberRepository.save(Member.builder()
                    .name(prefixName + i)
                    .build());
        }

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationNoOffsetBuilder(null, prefixName, 10);

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
        assertThat(members.get(0).getName()).isEqualTo("a30");
        assertThat(members.get(9).getName()).isEqualTo("a21");
    }

    @Test
    void paginationNoOffset2() {
        // given : 선행조건 기술
        String prefixName = "a";
//        for (int i = 1; i <= 30; i++) {
//            memberRepository.save(Member.builder()
//                    .name(prefixName + i)
//                    .build());
//        }

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationNoOffset(9990L, prefixName, 10);

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
//        assertThat(members.get(0).getName()).isEqualTo("a20");
//        assertThat(members.get(9).getName()).isEqualTo("a11");
    }
}