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

![ex_screenshot](../image/spring-batch/스프링배치 테이블 구조.png)