package com.example.springbatchexample.part3;

import com.example.springbatchexample.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = {SavePersonConfiguration.class, TestConfiguration.class})
public class SavePersonConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void tearDown() {
        long count = personRepository.count();
        System.out.println("count2 = " + count);
        personRepository.deleteAll();
        long count3 = personRepository.count();
        System.out.println("count3 = " + count3);
    }

    @Test
    public void test_step() {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("savePersonStep");

        assertThat(jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getWriteCount).sum())
                .isEqualTo(personRepository.count())
                .isEqualTo(4);
    }

    @Test
    void test_allow_duplicate() throws Exception {
        // given : 선행조건 기술
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("allow_duplicate", "false")
                .toJobParameters();

        // when : 기능 수행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        long count = personRepository.count();
        System.out.println("count1 = " + count);
        // then : 결과 확인
        assertThat(jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getWriteCount).sum())
                .isEqualTo(personRepository.count())
                .isEqualTo(4);
    }

    @Test
    void test_not_allow_duplicate() throws Exception {
        // given : 선행조건 기술
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("allow_duplicate", "true")
                .toJobParameters();

        // when : 기능 수행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then : 결과 확인
        assertThat(jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getWriteCount).sum())
                .isEqualTo(personRepository.count())
                .isEqualTo(99);
    }
}
