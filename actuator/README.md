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


# 프로메테우스 - 기본 기능
검색창에 http_server_requests_seconds_count 를 입력하고 실행해보자
```
http_server_requests_seconds_count{error="none", exception="none", instance="localhost:8080", job="spring-actuator", method="GET", outcome="CLIENT_ERROR", status="404", uri="/**"}
4
http_server_requests_seconds_count{error="none", exception="none", instance="localhost:8080", job="spring-actuator", method="GET", outcome="SUCCESS", status="200", uri="/actuator/metrics"}
4
http_server_requests_seconds_count{error="none", exception="none", instance="localhost:8080", job="spring-actuator", method="GET", outcome="SUCCESS", status="200", uri="/actuator/prometheus"}
308
http_server_requests_seconds_count{exception="None", instance="localhost:8000", job="apigateway-service", method="GET", outcome="CLIENT_ERROR", status="401", uri="UNKNOWN"}
21
http_server_requests_seconds_count{exception="None", instance="localhost:8000", job="apigateway-service", method="GET", outcome="SUCCESS", status="200", uri="/actuator/prometheus"}
20
http_server_requests_seconds_count{exception="NotFoundException", instance="localhost:8000", job="apigateway-service", method="GET", outcome="SERVER_ERROR", status="503", uri="UNKNOWN"}
21
```
태그, 레이블: error , exception , instance , job , method , outcome , status , uri 는 각각의 메트릭 정보를 구분해서 사용하기 위한 태그이다. 마이크로미터에서는 이것을 태그(Tag)라 하고, 프로메테우스에서는 레이블(Label)이라 한다. 여기서는 둘을 구분하지 않고 사용하겠다.
숫자: 끝에 마지막에 보면 132 , 4 와 같은 숫자가 보인다. 이 숫자가 바로 해당 메트릭의 값이다.

# 기본 기능

Table Evaluation time 을 수정해서 과거 시간 조회 가능  
Graph 메트릭을 그래프로 조회 가능

# 필터
레이블을 기준으로 필터를 사용할 수 있다. 필터는 중괄호( {} ) 문법을 사용한다.
# 레이블 일치 연산자
- = 제공된 문자열과 정확히 동일한 레이블 선택
- != 제공된 문자열과 같지 않은 레이블 선택
- =~ 제공된 문자열과 정규식 일치하는 레이블 선택
- !~ 제공된 문자열과 정규식 일치하지 않는 레이블 선택
## 예)
- uri=/log , method=GET 조건으로 필터
    - http_server_requests_seconds_count{uri="/log", method="GET"} 
- /actuator/prometheus 는 제외한 조건으로 필터
    - http_server_requests_seconds_count{uri!="/actuator/prometheus"} 
- method 가 GET , POST 인 경우를 포함해서 필터
    - http_server_requests_seconds_count{method=~"GET|POST"} 
- /actuator 로 시작하는 uri 는 제외한 조건으로 필터
    - http_server_requests_seconds_count{uri!~"/actuator.*"}

# 연산자 쿼리와 함수
다음과 같은 연산자를 지원한다. 
1. + (덧셈)
2. - (빼기)
3. * (곱셈)
4. / (분할)
5. % (모듈로)
6. ^ (승수/지수)
## sum
값의 합계를 구한다.
예) sum(http_server_requests_seconds_count)
## sum by
sum by(method, status)(http_server_requests_seconds_count) SQL의 group by 기능과 유사하다.
```
  {method="GET", status="404"} 3
  {method="GET", status="200"} 120
```

## count
count(http_server_requests_seconds_count)
메트릭 자체의 수 카운트
## topk
topk(3, http_server_requests_seconds_count)
상위 3개 메트릭 조회 
## 오프셋 수정자
http_server_requests_seconds_count offset 10m
offset 10m 과 같이 나타낸다. 현재를 기준으로 특정 과거 시점의 데이터를 반환한다.
## 범위 벡터 선택기
http_server_requests_seconds_count[1m]
마지막에 [1m] , [60s] 와 같이 표현한다. 지난 1분간의 모든 기록값을 선택한다.
참고로 범위 벡터 선택기는 차트에 바로 표현할 수 없다. 데이터로는 확인할 수 있다. 범위 벡터 선택의 결과를 차트에 표현하기 위해서는 약간의 가공이 필요한데, 조금 뒤에 설명하는 상대적인 증가 확인 방법을 참고하자.


# 프로메테우스 - 게이지와 카운터

메트릭은 크게 보면 게이지와 카운터라는 2가지로 분류할 수 있다.
## 게이지(Gauge)
- 임의로 오르내일 수 있는 값
    - 예) CPU 사용량, 메모리 사용량, 사용중인 커넥션, system_cpu_usage

## 카운터(Counter)
- 단순하게 증가하는 단일 누적 값 
- 예) HTTP 요청 수, 로그 발생 수, http_server_requests_seconds_count{uri = "/log"}

쉽게 이야기해서 게이지는 오르락 내리락 하는 값이고, 카운터는 특정 이벤트가 발생할 때 마다 그 수를 계속 누적하는 값이다.  
HTTP 요청 메트릭을 그래프로 표현해보자. 카운터는 계속 누적해서 증가하는 값이다.  
따라서 계속 증가하는 그래프만 보게 될 것이다.  
이렇게 증가만 하는 그래프에서는 특정 시간에 얼마나 고객의 요청이 들어왔는지 한눈에 확인하기 매우 어렵다.  
이런 문제를 해결하기 위해 increase() , rate() 같은 함수를 지원한다.

# increase()
increase() 를 사용하면 이런 문제를 해결할 수 있다. 지정한 시간 단위별로 증가를 확인할 수 있다. 마지막에 [시간] 을 사용해서 범위 벡터를 선택해야 한다.  
## 예) increase(http_server_requests_seconds_count{uri="/log"}[1m])  
분당 얼마나 고객의 요청이 어느정도 증가했는지 한눈에 파악할 수 있다.

# rate()

범위 백터에서 초당 평균 증가율을 계산한다.  
increase() 가 숫자를 직접 카운트 한다면, rate() 는 여기에 초당 평균을 나누어서 계산한다.  
increase(data[1m]) 에서 [1m] 이라고 하면 60초가 기준이 되므로 60을 나눈 수이다.  
increase(data[2m]) 에서 [2m] 이라고 하면 120초가 기준이 되므로 120을 나눈 수이다.
너무 복잡하게 생각하기 보다는 초당 얼마나 증가하는지 나타내는 지표로 보면 된다.

# irate()
rate 와 유사한데, 범위 벡터에서 초당 순간 증가율을 계산한다. 급격하게 증가한 내용을 확인하기 좋다.  자세한 계산 공식은 공식 메뉴얼을 참고하자.

# 정리
게이지: 값이 계속 변하는 게이지는 현재 값을 그대로 그래프로 표현하면 된다.  
카운터: 값이 단조롭게 증가하는 카운터는 increase() , rate() 등을 사용해서 표현하면 된다. 이렇게 하면 카운터에서 특정 시간에 얼마나 고객의 요청이 들어왔는지 확인할 수 있다.

# 참고
> 더 자세한 내용은 다음 프로메테우스 공식 메뉴얼을 참고하자
> 기본기능: https://prometheus.io/docs/prometheus/latest/querying/basics/
> 연산자: https://prometheus.io/docs/prometheus/latest/querying/operators/
> 함수: https://prometheus.io/docs/prometheus/latest/querying/functions/

<br/><br/><br/>
# 프로메테우스의 단점은 한눈에 들어오는 대시보드를 만들어보기 어렵다는 점이다. 이 부분은 그라파나를 사용하면 된다.


# 그라파나 - 설치 
https://grafana.com/grafana/download  
본인에게 맞는 OS를 선택해서 다운로드 한다. 
## 다운로드 - 윈도우 사용자
https://dl.grafana.com/enterprise/release/grafana-enterprise-9.3.6.windows-amd64.zip
## 다운로드 - MAC 사용자 
https://dl.grafana.com/enterprise/release/grafana-enterprise-9.3.6.darwin-amd64.tar.gz  
## 실행 - 윈도우
압축을 푼 곳에서 bin 폴더로 이동 grafana-server.exe 실행
윈도우의 경우 Window의 PC 보호 화면이 나올 수 있다. 앞의 프로메테우스 설치를 참고해서 문제를 해결하자.
## 실행 - MAC
압축을 푼 곳에서 bin 폴더로 이동
  ./grafana-server
별 반응이 없어도 웹 브라우저를 열어서 http://localhost:3000 에 접속하자.

# 실행
http://localhost:3000  
email or username: admin  
Password: admin  
그 다음에 Skip을 선택하면 된다.  

# 그라파나 연동
그라파나는 프로메테우스를 통해서 데이터를 조회하고 보여주는 역할을 한다. 쉽게 이야기해서 그라파나는 대시보드의 껍데기 역할을 한다.  
먼저 그라파나에서 프로메테우스를 데이터소스로 사용해서 데이터를 읽어와야 한다. 이 부분을 설정해보자.  
왼쪽 하단에 있는 설정(Configuration) 버튼에서 Data sources를 선택한다. Add data source 를 선택한다.  
Prometheus를 선택한다.  
Prometheus 데이터 소스 설정  
URL: http://localhost:9090  
나머지는 특별히 고칠 부분이 없다면 그대로 두고 Save & test 를 선택한다.  

# 그라파나 - 대시보드 만들기

## 대시보드 만들기
대시보드 저장
1. 왼쪽 Dashboards 메뉴 선택
2. New 버튼 선택 New Dashboard 선택
3. 오른쪽 상단의 Save dashboard 저장 버튼(disk 모양) 선택 4. Dashboard name: hello dashboard를 입력하고 저장
## 대시보드 확인
1. 왼쪽 Dashboards 메뉴 선택
2. 앞서 만든 hello dashboard 선택

## 대시보드에 패널 만들기
대시보드가 큰 틀이라면 패널은 그 안에 모듈처럼 들어가는 실제 그래프를 보여주는 컴포넌트이다. 
1. 오른쪽 상단의 Add panel 버튼(차트 모양) 선택
2. Add a new panel 메뉴 선택
3. 패널의 정보를 입력할 수 있는 화면이 나타난다.
4. 아래에 보면 Run queries 버튼 오른쪽에 Builder , Code 라는 버튼이 보이는데, Code 를 선택하자.
5. Enter a PromQL query... 이라는 부분에 메트릭을 입력하면 된다.


## CPU 메트릭 만들기
다음 메트릭을 패널에 추가해보자.  
- system_cpu_usage : 시스템의 CPU 사용량 
- process_cpu_usage : JVM 프로세스 CPU 사용량  

PromQL 에 system_cpu_usage 를 입력하고 Run queries 버튼을 선택하자 패널에 시스템 CPU 사용량을 그래프로 확인할 수 있다.  
process_cpu_usage 도 하나의 그래프에서 함께 확인하자. 이렇게 하려면 화면 하단의 + Query 버튼을 선택해야 한다.  
추가한 부분의 PromQL 에 process_cpu_usage 를 입력하고 Run queries 버튼을 선택하자  
패널에 프로세스 CPU 사용량이 추가된 것을 확인할 수 있다.  

### 그래프의 데이터 이름 변경  
패널 그래프 하단을 보면 범례(Legend)라고 하는 차트에 제공하는 데이터 종류를 구분하는 텍스트가 JSON으로 표시되어 있다.  
이 부분을 수정해보자.  
system_cpu_usage 를 입력한 곳에 가서 하단의 Options 를 선택한다.  
Legend 를 선택하고 Custom을 선택한다. system cpu 를 입력한다. process_cpu_usage 를 입력한 곳에 가서 하단의 Options 를 선택한다.  
Legend 를 선택하고 Custom을 선택한다. process cpu 를 입력한다.  
### 패널 이름 설정  
오른쪽에 보면 Panel options 라는 부분을 확인할 수 있다. 다음과 같이 수정하자  
Title : CPU 사용량
### 패널 저장하기
화면 오른쪽 상단의 Save 또는 Apply 버튼을 선택한다.


## 디스크 사용량 추가하기
패널을 추가하고 다음 항목을 입력하자  
### 패널 옵션  
Title : 디스크 사용량  
### PromQL
disk_total_bytes  
Legend : 전체 용량
+Query 로 다음을 추가하자  
disk_total_bytes - disk_free_bytes  
Legend : 사용 용량  
참고: 사용 디스크 용량 = 전체 디스크 용량 - 남은 디스크 용량  
### 그래프 데이터 사이즈 변경
그래프를 보면 데이터 사이즈가 byte로 보이기 때문에 불편할 것이다. 이것을 변경하려면 오른쪽 옵션 창을 확인하자  
Standard options Unit Data bytes(SI) 를 선택하자.  
GB, TB 단위로 읽기 편하게 변한 것을 확인할 수 있다.
### 최소값 변경
그래프는 현재 상태에 최적화가 된다. 하지만 디스크 사용량은 0부터 시작하는 것도 좋겠다. Standard options Min 0 을 선택하자.  
그래프가 0부터 시작하는 것을 확인할 수 있다.

그런데 이렇게 하나하나 직접 대시보드를 입력하는 것도 참으로 힘든 일이다. 그라파나는 이미 만들어둔 대시보드를 가져다가 사용할 수 있는 기능을 제공한다.