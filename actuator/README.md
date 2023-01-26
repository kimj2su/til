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