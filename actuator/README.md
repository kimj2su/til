# 메트릭 확인하기

CPU, JVM, 커넥션 사용 등등 수 많은 지표들을 어떻게 수집해야 할까?
개발자가 각각의 지표를 직접 수집해서 그것을 마이크로미터가 제공하는 표준 방법에 따라 등록하면 된다.  
다행히도 마이크로미터는 다양한 지표 수집 기능을 이미 만들어서 제공한다.  
 그리고 스프링 부트 액츄에이터는 마이크로미터가 제공하는 지표 수집을 @AutoConfiguration 을 통해 자동으로 등록해준다.  
쉽게 이야기해서 스프링 부트 액츄에이터를 사용하면 수 많은 메트릭(지표)를 편리하게 사용할 수 있다.  
이제 기본으로 제공하는 메트릭들을 확인해보자.  
아직 모니터링 툴을 연결한 것은 아니고, 등록된 메트릭들을 확인해보는 단계이다.

# metrics 엔드포인트

metrics 엔드포인트를 사용하면 기본으로 제공되는 메트릭들을 확인할 수 있다. 
http://localhost:8080/actuator/metrics

## 결과
```
{
    "names": [
        "application.ready.time",
        "application.started.time",
        "disk.free",
        "disk.total",
        "hikaricp.connections",
        "hikaricp.connections.acquire",
        "hikaricp.connections.active",
        "hikaricp.connections.idle",
        "hikaricp.connections.max",
        "hikaricp.connections.usage",
        "http.server.requests",
        "http.server.requests.active",
        "jdbc.connections.active",
        "jdbc.connections.idle",
        "jdbc.connections.max",
        "jdbc.connections.min",
        "jvm.buffer.count",
        "jvm.buffer.memory.used",
        "jvm.memory.used",
        "jvm.memory.max",
        "logback.events",
        "process.cpu.usage",
        "process.uptime",
        "system.cpu.count",
        "system.cpu.usage",
        "tomcat.sessions.active.current",
        "tomcat.sessions.rejected"
    ] 
}
```
액추에이터가 마이크로미터를 통해서 등록한 기본 메트릭들을 확인할 수 있다.  
 내용이 너무 많아서 일부만 남겨두었다.
metrics 엔드포인트는 다음과 같은 패턴을 사용해서 더 자세히 확인할 수 있다. 
http://localhost:8080/actuator/metrics/{name}


## Tag 필터
http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap
http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:nonheap

### HTTP 요청수를 확인

http://localhost:8080/actuator/metrics/http.server.requests  

HTTP 요청수에서 일부 내용을 필터링 해서 확인해보자. /log 요청만 필터 (사전에 /log 요청을 해야 확인할 수 있음)
http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/log  
/log 요청 & HTTP Status = 200  
http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/log&tag=status:200


# 다양한 메트릭
- 마이크로미터와 액츄에이터가 기본으로 제공하는 다양한 메트릭을 확인해보자. 
- JVM 메트릭
- 시스템 메트릭
- 애플리케이션 시작 메트릭
- 스프링 MVC 메트릭
- 톰캣 메트릭
- 데이터 소스 메트릭
- 로그 메트릭
- 사용자 정의 메트릭


# 스프링 MVC 메트릭

스프링 MVC 컨트롤러가 처리하는 모든 요청을 다룬다.  
 메트릭 이름: http.server.requests

TAG 를 사용해서 다음 정보를 분류해서 확인할 수 있다. 
- uri : 요청 URI
- method : GET , POST 같은 HTTP 메서드
- status : 200 , 400 , 500 같은 HTTP Status 코드 
- exception : 예외
- outcome : 상태코드를 그룹으로 모아서 확인 1xx:INFORMATIONAL , 2xx:SUCCESS , 3xx:REDIRECTION , 4xx:CLIENT_ERROR , 5xx:SERVER_ERROR

# 데이터소스 메트릭
DataSource , 커넥션 풀에 관한 메트릭을 확인할 수 있다. jdbc.connections. 으로 시작한다.  
최대 커넥션, 최소 커넥션, 활성 커넥션, 대기 커넥션 수 등을 확인할 수 있다.  
히카리 커넥션 풀을 사용하면 hikaricp. 를 통해 히카리 커넥션 풀의 자세한 메트릭을 확인할 수 있다.

# 로그 메트릭
logback.events : logback 로그에 대한 메트릭을 확인할 수 있다.  
trace, debug, info, warn, error 각각의 로그 레벨에 따른 로그 수를 확인할 수 있다. 예를 들어서 error 로그 수가 급격히 높아진다면 위험한 신호로 받아드릴 수 있다.

# 톰캣 메트릭
톰캣 메트릭은 tomcat. 으로 시작한다.
톰캣 메트릭을 모두 사용하려면 다음 옵션을 켜야한다. (옵션을 켜지 않으면 tomcat.session. 관련 정보만 노출된다.)

## tomcat.threads.config.max
현재 최대 쓰레드 개수 - 동시에 200개의 요청을 받을 수 있다.
```
{
    "name": "tomcat.threads.config.max",
    "baseUnit": "threads",
    "measurements": [
        {
            "statistic": "VALUE",
            "value": 200
        }
    ],
    "availableTags": [
        {
            "tag": "name",
            "values": [
                "http-nio-8080"
            ]
        }
    ]
}
```
## tomcat.threads.busy
실제 바쁘게 동작하는 쓰레드 이게 200개가 넘어가면 장애가 난다.
```
{
    "name": "tomcat.threads.busy",
    "baseUnit": "threads",
    "measurements": [
        {
            "statistic": "VALUE",
            "value": 1
        }
    ],
    "availableTags": [
        {
            "tag": "name",
            "values": [
                "http-nio-8080"
            ]
        }
    ]
}
```


# 프로메테우스
애플리케이션에서 발생한 메트릭을 그 순간만 확인하는 것이 아니라 과거 이력까지 함께 확인하려면 메트릭을 보관하는 DB가 필요하다.  
이렇게 하려면 어디선가 메트릭을 지속해서 수집하고 DB에 저장해야 한다.  
프로메테우스가 바로 이런 역할을 담당한다.
# 그라파나
프로메테우스가 DB라고 하면, 이 DB에 있는 데이터를 불러서 사용자가 보기 편하게 보여주는 대시보드가 필요하다.  
그라파나는 매우 유연하고, 데이터를 그래프로 보여주는 툴이다.  
수 많은 그래프를 제공하고, 프로메테우스를 포함한 다양한 데이터소스를 지원한다.

# 전체 구조 
1. 스프링 부트 액츄에이터와 마이크로미터를 사용하면 수 많은 메트릭을 자동으로 생성한다. 마이크로미터 프로메테우스 구현체는 프로메테우스가 읽을 수 있는 포멧으로 메트릭을 생성한다.
2. 프로메테우스는 이렇게 만들어진 메트릭을 지속해서 수집한다.
3. 프로메테우스는 수집한 메트릭을 내부 DB에 저장한다.
4. 사용자는 그라파나 대시보드 툴을 통해 그래프로 편리하게 메트릭을 조회한다. 이때 필요한 데이터는 프로메테우스를 통해서 조회한다.


# 프로메테우스 - 설치
https://prometheus.io/download/
본인에게 맞는 OS를 선택한다. 참고로 Mac OS 사용자는 darwin 을 선택하면 된다.
다운로드 - 윈도우 사용자 - windows-amd64 를 선택하면 된다. https://github.com/prometheus/prometheus/releases/download/v2.42.0/ prometheus-2.42.0.windows-amd64.zip
다운로드 - MAC 사용자 - darwin-amd64 를 선택하면 된다. https://github.com/prometheus/prometheus/releases/download/v2.42.0/ prometheus-2.42.0.darwin-amd64.tar.gz


실행 - 윈도우 prometheus.exe 실행, 추가정보 -> 실행
실행 - MAC ./prometheus 시스템환경설정 보안및개인정보보호 일반 -> 확인 없이 허용

접속 http://localhost:9090


# 프로메테우스 - 애플리케이션 설정

프로메테우스는 메트릭을 수집하고 보관하는 DB이다. 프로메테우스가 우리 애플리케이션의 메트릭을 수집하도록 연동해보자.
여기에는 2가지 작업이 필요하다.
1. 애플리케이션 설정: 프로메테우스가 애플리케이션의 메트릭을 가져갈 수 있도록 애플리케이션에서 프로메테우스 포멧에 맞추어 메트릭 만들기
2. 프로메테우스 설정: 프로메테우스가 우리 애플리케이션의 메트릭을 주기적으로 수집하도록 설정

애플리케이션 설정  
프로메테우스가 애플리케이션의 메트릭을 가져가려면 프로메테우스가 사용하는 포멧에 맞추어 메트릭을 만들어야 한다.  
참고로 프로메테우스는 /actuator/metrics 에서 보았던 포멧(JSON)은 이해하지 못한다.  
하지만 프로메테우스 포멧에 대한 부분은 걱정할 것이 없다. 마이크로미터가 이런 부분은 모두 해결해준다.  
각각의 메트릭들은 내부에서 마이크로미터 표준 방식으로 측정되고 있다. 따라서 어떤 구현체를 사용할지 지정만 해주면 된다.

```
implementation 'io.micrometer:micrometer-registry-prometheus' //추가
```
이크로미터 프로메테우스 구현 라이브러리를 추가한다.  
이렇게 하면 스프링 부트와 액츄에이터가 자동으로 마이크로미터 프로메테우스 구현체를 등록해서 동작하도록 설정해준다.  
액츄에이터에 프로메테우스 메트릭 수집 엔드포인트가 자동으로 추가된다.  
     /actuator/prometheus

실행  
http://localhost:8080/actuator/prometheus
```
  # HELP tomcat_threads_config_max_threads
  # TYPE tomcat_threads_config_max_threads gauge
  tomcat_threads_config_max_threads{name="http-nio-8080",} 200.0
  # HELP tomcat_sessions_alive_max_seconds
  # TYPE tomcat_sessions_alive_max_seconds gauge
  tomcat_sessions_alive_max_seconds 0.0
  # HELP tomcat_cache_access_total
  # TYPE tomcat_cache_access_total counter
  tomcat_cache_access_total 0.0
  # HELP jvm_info JVM version info
  # TYPE jvm_info gauge
  jvm_info{runtime="OpenJDK Runtime Environment",vendor="JetBrains
   s.r.o.",version="17.0.3+7-b469.37",} 1.0
  # HELP logback_events_total Number of events that made it to the logs
  # TYPE logback_events_total counter
  logback_events_total{level="warn",} 0.0
  logback_events_total{level="debug",} 0.0
  logback_events_total{level="error",} 2.0
  logback_events_total{level="trace",} 0.0
  logback_events_total{level="info",} 47.0
  ...
  ```

  모든 메트릭이 프로메테우스 포멧으로 만들어 진 것을 확인할 수 있다.  
/actuator/metrics 와 비교해서 프로메테우스에 맞추어 변환된 부분을 몇가지 확인해보자.  

# 포멧 차이
jvm.info jvm_info : 프로메테우스는 . 대신에 _ 포멧을 사용한다. . 대신에 _ 포멧으로 변환된 것을 확인할 수 있다.  
logback.events logback_events_total : 로그수 처럼 지속해서 숫자가 증가하는 메트릭을 카운터라 한다. 프로메테우스는 카운터 메트릭의 마지막에는 관례상 _total 을 붙인다.  
http.server.requests 이 메트릭은 내부에 요청수, 시간 합, 최대 시간 정보를 가지고 있었다.   프로메테우스에서는 다음 3가지로 분리된다.  
http_server_requests_seconds_count : 요청 수 http_server_requests_seconds_sum : 시간 합(요청수의 시간을 합함) http_server_requests_seconds_max : 최대 시간(가장 오래걸린 요청 수)  
대략 이렇게 포멧들이 변경된다고 보면 된다. 포멧 변경에 대한 부분은 진행하면서 자연스럽게 알아보자.  

# 프로메테우스 - 수집 설정

이제 프로메테우스가 애플리케이션의 /actuator/prometheus 를 호출해서 메트릭을 주기적으로 수집하도록 설정해보자.  
프로메테우스 폴더에 있는 prometheus.yml 파일을 수정하자.  

# prometheus.yml
```
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "prometheus"

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
      - targets: ["localhost:9090"]
  #추가
  - job_name: "spring-actuator"
    metrics_path: '/actuator/prometheus'
    scrape_interval: 1s
    static_configs:
    - targets: ['localhost:8080']

```


job_name : 수집하는 이름이다. 임의의 이름을 사용하면 된다. metrics_path : 수집할 경로를 지정한다.   
scrape_interval : 수집할 주기를 설정한다.  
targets : 수집할 서버의 IP, PORT를 지정한다.  
이렇게 설정하면 프로메테우스는 다음 경로를 1초에 한번씩 호출해서 애플리케이션의 메트릭들을 수집한다.  
http://localhost:8080/actuator/prometheus

> 주의  
> scrape_interval 여기서는 예제를 빠르게 확인하기 위해서 수집 주기를 1s 로 했지만, 수집 주기의 기본
값은 1m 이다. 수집 주기가 너무 짧으면 애플리케이션 성능에 영향을 줄 수 있으므로 운영에서는 10s ~ 1m 정도를 권장한다. (물론 시스템 상황에 따라서 다르다.)

프로메테우스 연동 확인  
프로메테우스 메뉴 Status Configuration 에 들어가서 prometheus.yml 에 입력한 부분이 추가되어 있는지 확인해보자.  
http://localhost:9090/config  
프로메테우스 메뉴 Status Targets 에 들어가서 연동이 잘 되었는지 확인하자. http://localhost:9090/targets  
prometheus : 프로메테우스 자체에서 제공하는 메트릭 정보이다. (프로메테우스가 프로메테우스 자신의 메트릭을 확인하는 것이다.)  
spring-actuator : 우리가 연동한 애플리케이션의 메트릭 정보이다.  
State 가 UP 으로 되어 있으면 정상이고, DOWN 으로 되어 있으면 연동이 안된 것이다.  