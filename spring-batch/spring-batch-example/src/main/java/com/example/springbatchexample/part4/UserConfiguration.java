package com.example.springbatchexample.part4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UsersRepository userRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job userJob() throws Exception {
        return jobBuilderFactory.get("userJob")
                .incrementer(new RunIdIncrementer())
                .start(this.saveUserStep())
                .next(this.userLevelUpStep())
                .listener(new LevelUpJobExecutionListener(userRepository))
                .build();
    }

    @Bean
    public Step saveUserStep() {
        return stepBuilderFactory.get("saveUserStep")
//                .chunk()
//                .reader()
//                .processor()
//                .writer()
                .tasklet(new SaveUserTasklet(userRepository))
                .build();
    }

    @Bean
    public Step userLevelUpStep() throws Exception {
        return stepBuilderFactory.get("userLevelUpStep")
                .<Users, Users>chunk(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemWriter<? super Users> itemWriter() {
        return users -> {
            users.forEach(x -> {
                x.levelUp();
                userRepository.save(x);
            });
        };
    }

    private ItemProcessor<? super Users, ? extends Users> itemProcessor() {
        return users -> {
            if (users.availableLevelUp()) {
                return users;
            }
            return null;
        };

    }

    private ItemReader<? extends Users> itemReader() throws Exception {
        JpaPagingItemReader<Users> itemReader = new JpaPagingItemReaderBuilder<Users>()
                .queryString("select u from Users u")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .name("userItemReader")
                .build();

        itemReader.afterPropertiesSet();
        return itemReader;
    }
}
