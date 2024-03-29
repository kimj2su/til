package com.example.springbatchexample.part3;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkProcessingConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkProcessingJob() {
        return jobBuilderFactory.get("chunkProcessingJob")
                .incrementer(new RunIdIncrementer())
                .start(this.taskBaseStep())
                .next(chunkBaseStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step chunkBaseStep(@Value("#{jobParameters[chunkSize]}") String chunkSize) {
        return stepBuilderFactory.get("chunkBaseStep")
                .<String, String>chunk(StringUtils.isNotEmpty(chunkSize) ? Integer.parseInt(chunkSize) : 10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<String> itemReader() {
        return new ListItemReader<>(getItems());
    }

    private ItemProcessor<String, String> itemProcessor() {
        return item -> item + ", Spring Batch";
    }

//    private ItemWriter<String> itemWriter() {
//        return items -> log.info("chunk items size : {}", items.size());
//    }

    private ItemWriter<String> itemWriter() {
        return items -> log.info("chunk item size : {}", items.size());
//        return items -> items.forEach(log::info);
    }

    @Bean
    public Step taskBaseStep() {
        return stepBuilderFactory.get("taskBaseStep")
                .tasklet(this.tasklet(null))
                .build();
    }

    // 기본 tasklet
//    private Tasklet tasklet() {
//        return ((contribution, chunkContext) -> {
//            List<String> items = getItems();
//            log.info("task item size : {}", items.size());
//
//            return RepeatStatus.FINISHED;
//        });
//    }

    //tasklet을 chunk처럼 사용하기
//    private Tasklet tasklet() {
//       List<String> items = getItems();
//
//       return ((contribution, chunkContext) -> {
//           StepExecution stepExecution = contribution.getStepExecution();
//
//           int chunkSize = 10;
//           int fromIndex = stepExecution.getReadCount();
//           int toIndex = fromIndex + chunkSize;
//
//           if (fromIndex >= items.size()) {
//               return RepeatStatus.FINISHED;
//           }
//
//           List<String> subList = items.subList(fromIndex, toIndex);
//
//           log.info("task item size : {} ", subList.size());
//
//           stepExecution.setReadCount(toIndex);
//
//           return RepeatStatus.FINISHED;
//       });
//    }

    //tasklet jobParameter 적용
//    private Tasklet tasklet() {
//        List<String> items = getItems();
//
//        return (contribution, chunkContext) -> {
//            StepExecution stepExecution = contribution.getStepExecution();
//            JobParameters jobParameters = stepExecution.getJobParameters();
//
//            //jobParameters 에서 chunkSize를 가져옴 없으면 10
//            String value = jobParameters.getString("chunkSize", "10");
//            int chunkSize = StringUtils.isNotEmpty(value) ? Integer.parseInt(value) : 10;
//
//            int fromIndex = stepExecution.getReadCount();
//            int toIndex = fromIndex + chunkSize;
//
//            if (fromIndex >= items.size()) {
//                return RepeatStatus.FINISHED;
//            }
//
//            List<String> subList = items.subList(fromIndex, toIndex);
//
//            log.info("task item size : {} ", subList.size());
//
//            stepExecution.setReadCount(toIndex);
//
//            return RepeatStatus.CONTINUABLE;
//        };
//    }


    //tasklet StepScope 적용
    @Bean
    @StepScope
    public Tasklet tasklet(@Value("#{jobParameters[chunkSize]}") String value) {
        List<String> items = getItems();

        return (contribution, chunkContext) -> {
            StepExecution stepExecution = contribution.getStepExecution();
//            JobParameters jobParameters = stepExecution.getJobParameters();

            //jobParameters 에서 chunkSize를 가져옴 없으면 10
//            String value = jobParameters.getString("chunkSize", "10");
            int chunkSize = StringUtils.isNotEmpty(value) ? Integer.parseInt(value) : 10;

            int fromIndex = stepExecution.getReadCount();
            int toIndex = fromIndex + chunkSize;

            if (fromIndex >= items.size()) {
                return RepeatStatus.FINISHED;
            }

            List<String> subList = items.subList(fromIndex, toIndex);

            log.info("task item size : {} ", subList.size());

            stepExecution.setReadCount(toIndex);

            return RepeatStatus.CONTINUABLE;
        };
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(i + " Hello");
        }
        return items;
    }
}
