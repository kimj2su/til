package com.example.nooffsetpaging;

import com.example.nooffsetpaging.domain.member.model.Member;
import com.example.nooffsetpaging.domain.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class NoOffsetPagingApplication {

    private final MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(NoOffsetPagingApplication.class, args);
    }

//    @Bean
//    ApplicationRunner init() {
//        return args -> {
//            var stopWatch = new StopWatch();
//            stopWatch.start();
//
//            String prefixName = "a";
//            int _1만 = 10000;
//            var members = IntStream.range(0, _1만 * 100)
//                    .parallel()
//                    .mapToObj(i -> Member.builder()
//                            .name(prefixName + i)
//                            .build())
//                    .toList();
//            stopWatch.stop();
//            System.out.println("객체 생성 시간 = " + stopWatch.getTotalTimeSeconds());
//
//            var queryStopWatch = new StopWatch();
//            queryStopWatch.start();
//            memberRepository.saveAll(members);
//            queryStopWatch.stop();
//            System.out.println("DB 인서트 시간 = " + queryStopWatch.getTotalTimeSeconds());
//        };
//    }
}
