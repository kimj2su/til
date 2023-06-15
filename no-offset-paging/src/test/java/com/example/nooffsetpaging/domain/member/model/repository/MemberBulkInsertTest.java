package com.example.nooffsetpaging.domain.member.model.repository;

import com.example.nooffsetpaging.domain.member.dto.MemberDto;
//import com.example.nooffsetpaging.domain.member.model.Member;
import com.example.nooffsetpaging.domain.member.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

//@ActiveProfiles("test")
@SpringBootTest
public class MemberBulkInsertTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void bulkInsert() {
        var stopWatch = new StopWatch();
        stopWatch.start();

        String prefixName = "a";
        int _1만 = 10000;
        var members = IntStream.range(0, _1만 * 100)
                .parallel()
                .mapToObj(i -> Member.builder()
                        .name(prefixName + i)
                        .build())
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성 시간 = " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();
        memberRepository.saveAll(members);
        queryStopWatch.stop();
        System.out.println("DB 인서트 시간 = " + queryStopWatch.getTotalTimeSeconds());
    }

}
