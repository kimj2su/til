package com.example.springbatchexample.part4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
public class LevelUpJobExecutionListener implements JobExecutionListener {

    private final UsersRepository userRepository;

    public LevelUpJobExecutionListener(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<Users> users = userRepository.findAllByUpdatedDate(LocalDate.now());

        //실행 시간
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("회원등급 업데이트 배치 프로그램");
        log.info("======================");
        log.info("총 데이터의 처리 {}건, 처리 시간 {}millis", users.size(), time);
    }
}
