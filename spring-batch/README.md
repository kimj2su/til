# 배치란?
- 큰 단위의 작업을 일괄 처리
- 대부분 처리량이 많고 비 실시간성 처리에 사용
    - 대용량 데이터 계산, 정산, 통계, 데이터베이스, 변환 등
- 컴퓨터 자원을 최대로 활용
    - 컴퓨터 자원 사용이 낮은 시간대에 배치를 처리하거나
    - 배치만 처리하기 위해 사용자가 사용하지 않는 또 다른 컴퓨터 자원을 사용할 수 있다.
- 사용자 상호작용으로 실행되기 보단, 스케줄러와 같은 시스템에 의해 실행되는 대상
    - 예를 들면 매일 오전 10시에 배치 실행, 매주 월요일 12시 마다 실행
    - crontab, jenkins …

<br/><br/>

# 스프링 배치란?
- 배치 처리를 하기 위한 Spring Framework 기반 기술
    - Spring에서 지원하는 기술 적용 가능
    - DI, AOP, 서비스 추상화
- 간단한 작업(Tasklet) 기반 처리와, 대량 처리(Chunk) 기반 기능 지원

<br/><br/>

# 스프링 배치 시작하기
```
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchExampleApplication.class, args);
    }

}
```
@EnableBatchProcessing 어노테이션을 사용하면 이 어플리케이션은 배치프로세싱을 사용하겠다는 설정입니다.  


```
@Slf4j
@Configuration
@RequiredArgsConstructor
public class HelloConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())
                .start()
                .build();
    }
}
```

# Job 이란?
Job은 스프링 배치에서 배치의 실행 단위라고 생각하면 됩니다.  
잡을 생성하기 위해 스프링 배치에서는 JobBuilderFactory 클래스를 제공 합니다.  
이 클래스는 @EnableBatchProcessing을 통해서 스프링 빈으로 생성되었기 때문에 의존성 주입을 받을 수 있게 됩니다.  
잡은 실행단위를 구분할 수 있는 incrementer 와 잡의 이름, 스탭을 설정해야합니다.  
new RunIdIncrementer() 클래스는 잡이 실행할 때 마다 파라미터 아이디를 자동으로 생성해주는 클래스입니다.  
잡의 이름으 heeloJob입니다.  
이 이름(name)은 스프링 배치를 실행시킬 수 있는 키이기도 합니다.  
start() 메서드는 잡 실행시 최초로 실행할 스텝을 설정하는 메서드 입니다.  
step은 잡의 실행 단위입니다.  
하나의 잡은 한개 이상의 step을 가질 수 있습니다.  
step도 잡처럼 빈으로 등록을 해줘야 합니다.  

<br/><br/>

# Job 실행시키기
Program arguments
```
--spring.batch.job.names=helloJob
```

기본적으로 위와 같은 설정 값이 없으면 모든 잡이 실행 되게 됩니다.


## application.yml
```
spring:
  batch:
    job:
      names: ${job.name:NONE}
```
위와 같이 spring.batch.job.names를 job.name으로 키를 변경했습니다.

Program arguments
```
before
--spring.batch.job.names=helloJob

after
--job.name=helloJob
```
위와 같이 설정할 수 있습니다.

<br/><br/>

# 스프링 배치 아키텍쳐

스프링 배치는 Job타입의 빈이 생성되면 JobLauncher 객체에 의해서 잡을 실행합니다.  
JobLauncher -> Job -> Step 을 실행 시킵니다.  

## Job
- Job은 JobLauncher에 의해 실행
- Job은 배치의 실행 단위를 의미
- Job은 N개의 Step을 실행할 수 있으며, 흐름(Flow)을 관리할 수 있다.
    - 예를 들면, A Step 실행 후 조건에 따라 B Step 또는 C Step을 실행 설정
## Step
- Step은 Job의 세부 실행 단위이며, N개가 등록돼 실행된다.
- Step의 실행 단위는 크게 2가지로 나눌 수 있다.
    1. Chunk 기반 : 하나의 큰 덩어리를 n개씩 나눠서 실행
    2. Task 기반 : 하나의 작업 기반으로 실행
- Chunk 기반 Step은 ItemReader, ItemProcessor, ItemWriter가 있다.
    - 여기서 Item은 배치 처리 대상 객체를 의미한다.
- ItemReader는 배치 처리 대상 객체를 읽어 ItemProcessor 또는 ItemWriter에게 전달한다.
    - 예를 들면, 파일 또는 DB에서 데이터를 읽는다.
- ItemProcessor는 input 객체를 output 객체로 filtering 또는 processing 해 ItemWriter에게 전달한다.
    - 예를 들면, ItemReader에서 읽은 데이터를 수정 또는 ItemWriter 대상인지 filtering 한다.
    - ItemProcessor는 optional 하다.
    - ItemProcessor가 하는 일을 ItemReader 또는 ItemWriter가 대신할 수 있다.
- ItemWriter는 배치 처리 대상 객체를 처리한다.
    - 예를 들면, DB update를 하거나, 처리 대상 사용자에게 알림을 보낸다.

# 스프링 배치 테이블 구조 
![spring-batch-table](https://github.com/jisu3316/til/blob/main/image/spring-batch/스프링배치테이블구조.png?raw=true)  

- 배치 실행을 위한 메타 데이터가 저장되는 테이블
- BATCH_JOB_INSTANCE
    - Job이 실행되며 생성되는 최상위 계층의 테이블
    - job_name과 job_key를 기준으로 하나의 row가 생성되며, 같은 job_name과 job_key가 저장될 수 없다.
    - job_key는 BATCH_JOB_EXECUTION_PARAMS에 저장되는 Parameter를 나열해 암호화해 저장한다.
- BATCH_JOB_EXECUTION
    - Job이 실행되는 동안 시작/종료 시간, job 상태 등을 관리
- BATCH_JOB_EXECUTION_PARAMS
    - Job을 실행하기 위해 주입된 parameter 정보 저장
- BATCH_JOB_EXECUTION_CONTEXT
    - Job이 실행되며 공유해야할 데이터를  직렬화해 저장
- BATCH_STEP_EXECUTION
    - Step이 실행되는 동안 필요한 데이터 또는 실행된 결과 저장
- BATCH_STEP_EXECUTION_CONTEXT
    - Step이 실행되며 공유해야할 데이터를 직렬화해 저장
<br/>

spring- batch-core/org.springframework/batch/core/* 에 위치
- 스프링 배치를 실행하고 관리하기 위한 테이블
- schema.sql 설정
    - schema-**.sql의 실행 구분은 
        - DB 종류별로 script가 구분
    - spring.batch.initialize-schema config로 구분한다.
    - ALWAYS, EMBEDDED, NEVER로 구분한다.
        - ALWAYS : 항상 실행
        - EMBEDDED : 내장 DB일 때만 실행
        - NEVER : 항상 실행 안함
    - 기본 값은 EMBEDDED다.

```
-- Autogenerated: do not edit this file

CREATE TABLE BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID BIGINT       NOT NULL PRIMARY KEY,
    VERSION         BIGINT,
    JOB_NAME        VARCHAR(100) NOT NULL,
    JOB_KEY         VARCHAR(32)  NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE = InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID           BIGINT        NOT NULL PRIMARY KEY,
    VERSION                    BIGINT,
    JOB_INSTANCE_ID            BIGINT        NOT NULL,
    CREATE_TIME                DATETIME(6)   NOT NULL,
    START_TIME                 DATETIME(6) DEFAULT NULL,
    END_TIME                   DATETIME(6) DEFAULT NULL,
    STATUS                     VARCHAR(10),
    EXIT_CODE                  VARCHAR(2500),
    EXIT_MESSAGE               VARCHAR(2500),
    LAST_UPDATED               DATETIME(6),
    JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
        references BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID BIGINT       NOT NULL,
    TYPE_CD          VARCHAR(6)   NOT NULL,
    KEY_NAME         VARCHAR(100) NOT NULL,
    STRING_VAL       VARCHAR(250),
    DATE_VAL         DATETIME(6) DEFAULT NULL,
    LONG_VAL         BIGINT,
    DOUBLE_VAL       DOUBLE PRECISION,
    IDENTIFYING      CHAR(1)      NOT NULL,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  BIGINT       NOT NULL PRIMARY KEY,
    VERSION            BIGINT       NOT NULL,
    STEP_NAME          VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID   BIGINT       NOT NULL,
    START_TIME         DATETIME(6)  NOT NULL,
    END_TIME           DATETIME(6) DEFAULT NULL,
    STATUS             VARCHAR(10),
    COMMIT_COUNT       BIGINT,
    READ_COUNT         BIGINT,
    FILTER_COUNT       BIGINT,
    WRITE_COUNT        BIGINT,
    READ_SKIP_COUNT    BIGINT,
    WRITE_SKIP_COUNT   BIGINT,
    PROCESS_SKIP_COUNT BIGINT,
    ROLLBACK_COUNT     BIGINT,
    EXIT_CODE          VARCHAR(2500),
    EXIT_MESSAGE       VARCHAR(2500),
    LAST_UPDATED       DATETIME(6),
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
        references BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ
(
    ID         BIGINT  NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE = InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY)
select *
from (select 0 as ID, '0' as UNIQUE_KEY) as tmp
where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ
(
    ID         BIGINT  NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE = InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY)
select *
from (select 0 as ID, '0' as UNIQUE_KEY) as tmp
where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ
(
    ID         BIGINT  NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE = InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY)
select *
from (select 0 as ID, '0' as UNIQUE_KEY) as tmp
where not exists(select * from BATCH_JOB_SEQ);
```    

# 스크립트 생성 시점 생성
```
spring:
  batch:
    job:
      names: ${job.name:NONE}
    jdbc:
      initialize-schema: always, embaded, never
```      
always : 항상 실행한다.
never : 절대 실행 시키지 않음
embaded : h2같은 임베디드 디비 사용

<br/><br/>

# Job, JobInstance, JobExecution, Step, StepExecution 이해
- ## JobInstance : BATCH_JOB_INSTANCE 테이블과 매핑  
- ## JobExecution : BATCH_JOB_EXECUTION 테이블과 매핑  
- ## JobParameters : BATCH_JOB_EXECUTION_PARAMS 테이블과 매핑  
- ## ExecutionContext : BATCH_JOB_EXECUTION_CONTEXT 테이블과 매핑  

- JobInstance의 생성 기준은 JobParamters 중복 여부에 따라 생성된다.
- 다른 parameter로 Job이 실행되면, JobInstance가 생성된다.
- 같은 parameter로 Job이 실행되면, 이미 생성된 JobInstance가 실행된다.
- JobExecution은 항상 새롭게 생성된다.
- 예를 들어
    - 처음 Job 실행 시 date parameter가 1월1일로 실행 됐다면, 1번 JobInstance가 생성된다.
    - 다음 Job 실행 시 date parameter가 1월2일로 실행 됐다면, 2번 JobInstance가 생성된다.
    - 다음 Job 실행 시 date parameter가 1월2일로 실행 됐다면, 2번 JobInstance가 재 실행된다.
        - 이때 Job이 재실행 대상이 아닌 경우 에러가 발생한다.
- Parameter가 없는 Job을 항상 새로운 JobInstance가 실행되도록 RunIdIncrementer가 제공된다.

- StepExecution : BATCH_STEP_EXECUTION 테이블과 매핑
- ExecutionContext : BATCH_STEP_EXECUTION_CONTEXT 테이블과 매핑

배치 실행 후 select * from BATCH_JOB_EXECUTION_PARAMS; 조회하면 run.id 가 1씩늘어나는것을 확인.  

<br/><br/>

# Task 기반 배치와 Chunk 기반 배치

- 배치를 처리할 수 있는 방법은 크게 2가지
- Tasklet을 사용한 Task 기반 처리
    - 배치 처리 과정이 비교적 쉬운 경우 쉽게 사용
    - 대량 처리를 하는 경우 더 복잡
    - 하나의 큰 덩어리를 여러 덩어리로 나누어 처리하기 부적합
- Chunk를 사용한 chunk(덩어리) 기반 처리
    - ItemReader, ItemProcessor, ItemWriter의 관계 이해 필요
    - 대량 처리를 하는 경우 Tasklet 보다 비교적 쉽게 구현
    - 예를 들면 10,000개의 데이터 중 1,000개씩 10개의 덩어리로 수행
        - 이를 Tasklet으로 처리하면 10,000개를 한번에 처리하거나, 수동으로 1,000개씩 분할
- 예제 참고

# 예제 
List<String> 100개의 문자열을 만들어 사이즈를 찍어본다.
## tasklet 사용
```
@Bean
    public Job chunkProcessingJob() {
        return jobBuilderFactory.get("chunkProcessingJob")
                .incrementer(new RunIdIncrementer())
                .start(this.taskBaseStep())
                .build();
    }

    @Bean
    public Step taskBaseStep() {
        return stepBuilderFactory.get("taskBaseStep")
                .tasklet(this.tasklet())
                .build();
    }

    private Tasklet tasklet() {
        return ((contribution, chunkContext) -> {
            List<String> items = getItems();
            log.info("task item size : {}", items.size());

            return RepeatStatus.FINISHED;
        });
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(i + " Hello");
        }
        return items;
    }


결과
Job: [SimpleJob: [name=chunkProcessingJob]] launched with the following parameters: [{run.id=1}]
Executing step: [taskBaseStep]
task item size : 100
Step: [taskBaseStep] executed in 6ms
Job: [SimpleJob: [name=chunkProcessingJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED] in 14ms
```

## Chunk 기반의 배치
100개의 데이터를 10번씩 나눠서 처리한다.
```
@Bean
public Step chunkBaseStep() {
    return stepBuilderFactory.get("chunkBaseStep")
            .<String, String>chunk(10)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter()0)
            .build();
}
여기서 <String, String>chunk(10) 한번에 몇번씩 처리할것인가에 대해 정의 해주는 곳이다.

private ItemReader<String> itemReader() {
    return new ListItemReader<>(getItems());
}

스프링에서 기본적으로 제공해주는 ListItemReader 이다. 파라미터로 List를 받는다.


private ItemProcessor<String, String> itemProcessor() {
    return item -> item + ", Spring Batch";
}

아이템 프로세서는 리더에서 읽은 아이템을 가공해주거나 Writer로 넘길지 말지 결정을 하는 역할을 합니다.  
만약에 ItemProcessor에서 null로 리턴이 되면 해당 아이템은 Writer로 넘어갈 수 없게 됩니다.  
여기서는 getItems()에서 생성한 값에 Spring Batch를 더해주게 되어서 모든 값이 Writer로 넘어가게 됩니다.  

private ItemWriter<String> itemWriter() {
    return items -> log.info("chunk items size : {}", items.size());
}
private ItemWriter<String> itemWriter() {
        return items -> items.forEach(log::info);
}

```
![spring-batch](https://github.com/jisu3316/til/blob/main/image/spring-batch/Chunk기반배치이해.png?raw=true)  

- reader에서 null을 return 할 때 까지 Step은 반복
- <INPUT, OUTPUT>chunk(int)
    - reader에서 INPUT 을 return
    - processor에서 INPUT을 받아 processing 후 OUPUT을 return
        - INPUT, OUTPUT은 같은 타입일 수 있음
    - writer에서 List<OUTPUT>을 받아 write

<br/><br/>

# JobParameters 이해

- 배치를 실행에 필요한 값을 parameter를 통해 외부에서 주입
- JobParameters는 외부에서 주입된 parameter를 관리하는 객체
- parameter를 JobParameters와 Spring EL(Expression Language)로 접근
    - String parameter = jobParameters.getString(key, defaultValue);
    - @Value(“#{jobParameters[key]}”) 
- 예제 참고
## -chunkSize=20 --job.name=chunkProcessingJob
```
 private Tasklet tasklet() {
    List<String> items = getItems();

    return (contribution, chunkContext) -> {
        StepExecution stepExecution = contribution.getStepExecution();
        JobParameters jobParameters = stepExecution.getJobParameters();

        //jobParameters 에서 chunkSize를 가져옴 없으면 10
        String value = jobParameters.getString("chunkSize", "10");
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
```

# @JobScope와 @StepScope
- @Scope는 어떤 시점에 bean을 생성/소멸 시킬 지 bean의 lifecycle을 설정
- @JobScope는 job 실행 시점에 생성/소멸
    - Step에 선언
- @StepScope는 step 실행 시점에 생성/소멸
    - Tasklet, Chunk(ItemReader, ItemProcessor, ItemWriter) 에 선언
- Spring의 @Scope과 같은 것
    - @Scope(“job”) == @JobScope
    - @Scope(“step”) == @StepScope
- Job과 Step 라이프사이클에 의해 생성되기 때문에 Thread safe하게 작동
= @Value(“#{jobParameters[key]}”)를 사용하기 위해 @JobScope와 @StepScope는 필수

```
 @Bean
public Step taskBaseStep() {
    return stepBuilderFactory.get("taskBaseStep")
            .tasklet(this.tasklet(null))
            .build();
}

//tasklet StepScope 적용
@Bean
@StepScope
public Tasklet tasklet(@Value("#{jobParameters[chunkSize]}") String value) {
    List<String> items = getItems();

    return (contribution, chunkContext) -> {
        StepExecution stepExecution = contribution.getStepExecution();
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

```
@JobScope와 @StepScope는 빈으로 등록해줘야함.  
.tasklet(this.tasklet(null)) 파라미터 null인 이유는 빈의 라이프사이클로 인해 스프링이 @Value 어노테이션에 값을 할당해줍니다.  

<br/><br/>

# ItemReader Interface구조
- 배치 대상 데이터를 읽기 위한 설정
    - 파일, DB, 네트워크, 등에서 읽기 위함.
- Step에 ItemReader는 필수
- 기본 제공되는 ItemReader 구현체
    - file, jdbc, jpa, hibernate, kafka, etc... 
- ItemReader 구현체가 없으면 직접 개발
- ItemStream은 ExecutionContext로 read, write 정보를 저장
- CustomItemReader 예제 참고


# CustomItemReader
```
public class CustomItemReader<T> implements ItemReader<T> {

    private final List<T> items;

    public CustomItemReader(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!items.isEmpty()) {
            //remove는 첫번쨰 아이템을 반환해주면서 제거한다.
            return items.remove(0);
        }

        return null;
    }
}
```

# csv파일 읽기
```
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ItemReaderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderJob() throws Exception {
        return jobBuilderFactory.get("itemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(this.customItemReaderStep())
                .next(this.csvFileStep())
                .build();
    }

    @Bean
    public Step customItemReaderStep() {
        return stepBuilderFactory.get("customItemReaderStep")
                .<Person, Person>chunk(10)
                .reader(new CustomItemReader<>(getItems()))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step csvFileStep() throws Exception {
        return stepBuilderFactory.get("csvFileStep")
                .<Person, Person>chunk(10)
                .reader(csvFileItemReader())
                .writer(itemWriter())
                .build();
    }

    //csv 파일 리더
    private FlatFileItemReader<Person> csvFileItemReader() throws Exception {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "name", "age", "address");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            int id = fieldSet.readInt("id");
            String name = fieldSet.readString("name");
            String age = fieldSet.readString("age");
            String address = fieldSet.readString("address");

            return new Person(id, name , age, address);
        });

        FlatFileItemReader<Person> itemReader = new FlatFileItemReaderBuilder<Person>()
                .name("csvFileItemReader")
                .encoding("UTF-8")
                .resource(new ClassPathResource("test.csv"))
                .linesToSkip(1) // 1번쨰 라인을 스킵한다.
                .lineMapper(lineMapper)
                .build();
        itemReader.afterPropertiesSet();

        return itemReader;
    }

    private ItemWriter<Person> itemWriter() {
        return items -> log.info(items.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", ")));
    }

    private List<Person> getItems() {
        List<Person> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Person(i + 1, "test name" + i, "test age", "test address"));
        }

        return items;
    }
}
```

<br/><br/>

# JDBC 데이터 읽기 - Cursor
## Cursor 기반 조회
- 배치 처리가 완료될 때 까지 DB Connection이 연결
- DB Connection 빈도가 낮아 성능이 좋은 반면, 긴 Connection 유지 시간 필요
- 하나의 Connection에서 처리되기 때문에, Thread Safe 하지 않음
- 모든 결과를 메모리에 할당하기 때문에, 더 많은 메모리를 사용
## Paging 기반 조회
- 페이징 단위로 DB Connection을 연결
- DB Connection 빈도가 높아 비교적 성능이 낮은 반면, 짧은 Connection 유지 시간 필요
- 매번 Connection을 하기 때문에 Thread Safe
- 페이징 단위의 결과만 메모리에 할당하기 때문에, 비교적 더 적은 메모리를 사용

|              |   JdbcCursorItemReader  |   JdbcPagingItemReader   |
|--------------|-------------------------|--------------------------|
|  datasource  |   JDBC를 실행하기 위한 Datasource|                      |
|  beanMapper, rowMapper | 조회된 데이터 row를 클래스와 매핑하기 위한 설정|    |
|  sql  |   조회 쿼리 설정                  |         x                 |
|  selectClause, fromClause, whereClause 또는
queryProvider |         X                |   조회 쿼리 설정             |
|  fetchSize  |  cursor에서 fetch될 size   |    JdbcTemplate.fetchSize |
|  pageSize  |     X                    | paging에 사용될 page 크기(offset/limit) |
  
<br/><br/>

# JPA 데이터 읽기

- 4.3+ 에서 Jpa 기반 Cursor ItemReader가 제공됨.
- 기존에는 Jpa는 Paging 기반의 ItemReader만 제공됨.  

|              |   JpaCursorItemReader  |   JpaPagingItemReader   |
|--------------|-------------------------|--------------------------|
|  entityManagerFactory  |   JPA를 실행하기 위해 EntityManager를 생성하기 위한 EntityManagerFactory|                      |
|   | 조회된 데이터 row를 클래스와 매핑하기 위한 설정
|    |
|  queryString  |   조회 쿼리                  |         x                 |
|  selectClause, fromClause, whereClause |         X                |   조회 쿼리 |
|  fetchSize  |  cursor에서 fetch될 size   |    JdbcTemplate.fetchSize |
|  pageSize  |     X                    | paging에 사용될 page 크기(offset/limit) |

<br/><br/>

# ItemWriter Interface 구조 이해

- ItemWriter는 마지막으로 배치 처리 대상 데이터를 어떻게 처리할 지 결정
- Step에서 ItemWriter는 필수
- 예를 들면 ItemReader에서 읽은 데이터를
    - DB에 저장, API로 서버에 요청, 파일에 데이터를 write
- 항상 write가 아님
    - 데이터를 최종 마무리를 하는 것이 ItemWriter



<br/><br/>

# csvFile 쓰기
```
private ItemWriter<Person> csvFileItemWriter() throws Exception {
    BeanWrapperFieldExtractor<Person> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(new String[] {"id", "name", "age", "address"});

    DelimitedLineAggregator<Person> lineAggregator = new DelimitedLineAggregator<>();
    lineAggregator.setDelimiter(",");
    lineAggregator.setFieldExtractor(fieldExtractor);

    FlatFileItemWriter<Person> itemWriter = new FlatFileItemWriterBuilder<Person>()
            .name("csvFileItemWriter")
            .encoding("UTF-8")
            .resource(new FileSystemResource("output/test-output.csv"))
            .headerCallback(writer -> writer.write("id,이름,나이,거주기"))
            .footerCallback(writer -> writer.write("----------------\n"))
            .append(true)
            .lineAggregator(lineAggregator)
            .build();

    itemWriter.afterPropertiesSet();
    return itemWriter;
}
```

# ItemProcessor interface 구조 이해
- ItemReader에서 읽은 데이터를 가공 또는 Filtering
- Step의 ItemProcessor는 optional
- ItemProcessor는 필수는 아니지만, 책임 분리를 분리하기 위해 사용
- ItemProcessor는 I(input)를 O(output)로 변환하거나
- ItemWriter의 실행 여부를 판단 할 수 있도록 filtering 역할을 한다.
    - ItemWriter는 not null만 처리한다.

# JobExecutionsListener, StepExecutionsListener
- 스프링 배치에서 전 처리, 후 처리를 하는 다양한 종류의 Listener 존재.
    - interface 구현
    - @Annotation 정의
- Job 실행 전과 후에 실행할 수 있는 JobExecutionListener
- Step 실행 전과 후에 실행할 수 있는 StepExecutionListener


# StepListener 이해

- Step에 관련된 모든 Listener는 StepListener를 상속
- StepExecutionListener
- SkipListener
- ItemReadListener
- ItemProcessListener
- ItemWriteListener
- ChunkListener

## SkipListener
- onSkipInRead : @OnSkipInRead
    - ItemReader에서 Skip이 발생한 경우 호출
- onSkipInWrite : @OnSkipInWrite
    - ItemWriter에서 Skip이 발생한 경우 호출
- onSkipInProcess : @OnSkipInProcess
    - ItemProcessor에서 Skip이 발생한 경우 호출

## ItemReadListener
- beforeRead : @BeforeRead
    - ItemReader.read() 메소드 호출 전 호출
- afterRead : @AfterRead
    - ItemReader.read() 메소드 호출 후 호출
- onReadError : @OnReadError
    - ItemReader.read() 메소드에서 에러 발생 시 호출

## ItemWriteListener
- beforeWrite : @BeforeRead
    - ItemWriter.write() 메소드 호출 전 호출
- afterWrite : @AfterRead
    - ItemWriter.write() 메소드 호출 후 호출
- onWriteError : @OnWriteError
    - ItemWriter.write() 메소드에서 에러 발생 시 호출

## ItemProcessListener
- beforeProcess : @BeforeProcess
    - ItemProcess.process() 메소드 호출 전 호출
- afterProcess : @AfterProcess
    - ItemProcess.process() 메소드 호출 후 호출
- onProcessError : @OnProcessError
    - ItemProcess.process() 메소드에서 에러 발생 시 호출

## ChunkListener
- beforeChunk : @BeforeChunk
    - chunk 실행 전 호출
- afterChunk : @BfterChunk
    - chunk 실행 후 호출 
- afterChunkError : @BfterChunkError
    - chunk 실행 중 에러 발생 시 호출


<br/><br/>

# skip 예외 처리
- step 수행 중 발생한 특정 Exception과 에러 횟수 설정으로 예외처리 설정
- skip(NotFoundNameException.class), skipLimit(3) 으로 설정된 경우
    - NotFoundNameException 발생 3번까지는 에러를 skip 한다.
    - NotFoundNameException 발생 4번째는 Job과 Step의 상태는 실패로 끝나며, 배치가 중지된다.
    - 단, 에러가 발생하기 전까지 데이터는 모두 처리된 상태로 남는다.
- Step은 chunk 1개 기준으로 Transaction 동작
    - 예를 들어 items = 100, chunk.size =10, 총 chunk 동작 횟수 = 10
        - chunk 1-9는 정상 처리, chunk 10에서 Exception이 발생한 경우
        - chunk 1-9 에서 처리된 데이터는 정상 저장되고, Job과 Step의 상태는 FAILED 처리
    - 배치 재 실행 시 chunk 10 부터 처리할 수 있도록 배치를 만든다.
```
faultTolerant()
.skip(NotFoundNameException.class)
.skipLimit(2)
```

faultTolerant() 밑에 skip을 작성해야 작동한다.

# retry 예외 처리
- Step 수행 중 간헐적으로 Exception 발생 시 재시도(retry) 설정
    - DB Deadlock, Network timeout 등
- retry(NullPointerException.class), retryLimit(3) 으로 설정된 경우
    - NotFoundNameExeception이 발생한 경우 3번까지 재시도
- 더 구체적으로 retry를 정의하려면 RetryTemplate 이용
- 추가 요구사항
    - NotFoundNameException이 발생하면, 3번 재 시도 후 Person.name을 “UNKNOWN” 으로 변경