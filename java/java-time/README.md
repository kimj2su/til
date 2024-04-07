# 자바 날짜와 시간 라이브러리의 역사
자바는 날짜와 시간 라이브러리를 지속해서 업데이트 했다.  

## JDK 1.0 (java.ujtil.Date)
- 문제점
  - 타임존 부족 : 초기 Date 클래스는 타임존을 제대로 처리하지 못했다.
  - 불편한 날짜 시간 연산 : 날짜 간 연산이나 시간의 증감등을 처리하기 어려웠다.
  - 불변 객체 부재 : Date 객체는 변경가능(mutable)하여, 데이터가 쉽게 변경도리 수 있었고 이로 인해 버그가 발생하기 쉬웠다.
- 해결책
  - JDK 1.1에서 Calendar 클래스가 추가되었다.
  - Calendar 클래스는 Date 클래스의 문제점을 해결하고, 날짜와 시간을 다루는 다양한 메서드를 제공했다.

## JDK 1.1 (java.util.Calendar)
- 문제점
  - 사용성 저하 : Calender는 사용하기 복잡하고 직관적이지 않았다.
  - 성능 문제 : 일부 사용 사례에서 성능이 저하되는 문제가 있었다.
  - 불변 객체 부재 : Calendar 객체는 변경가능(mutable)하여, 사이드 이펙트, 스레드 안정성 문제가 있었다.
- 해결책
  - Joda-Time 라이브러리가 등장했다.

## Joda-Time
- 문제점
  - 표준 라이브러리가 아님 : Joda-Time은 외부 라이브러리로써, JDK에 포함되지 않았다.
- 해결책
  - JSR-310 : Java 8에서 Joda-Time을 기반으로 새로운 날짜와 시간 API를 제공하기로 결정했다.

## Java 8 (java.time)
- java.time 패키지는 이전 API의 문제점을 해결하면서, 사용성, 성능, 스레드 안정성, 타임존 처리 등에서 크레 개선되었다.
- 변겨 불가능한 불변 객체 설계로 사이드 이펙트, 스레드 안정성 보장 보다 직관적인 API 제공으로 날짜와 시간 연상을 단순화 했다.
- LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Instant 등의 클래스를 포함한다.
- Joda-Time의 많은 기능을 표준 자바 플랫폼으로 가져왔다.

# LocalDate, LocalDateTime, LocalDateTime
- LocalDate : 날짜 정보만 포함한다. 년, 월, 일을 다룬다. 예) 2024-04-01
- LocalTime: 시간만을 표현한다. 시, 분, 초를 다룬다. 예)21:34:55 초는 밀리초, 나노초 단위도 포함할 수 있다.
- LocalDateTime: LocalDate와 LocalTime을 합쳐서 날짜와 시간을 다룬다. 예) 2024-04-01T21:34:55

앞에 Local(현지의, 특정 지역의)이 붙는 이유는 세계 시간대를 고려하지 않아서 타임존이 적용되지 않기 때문이다.
특정 지역의 날짜와 시간만 고려할 때 사용한다.
- 애플리케시연 개발시 국내 서비스만 고려할 때

# ZonedDateTime, OffsetDateTime
- ZonedDateTime : 타임존까지 고려한 날짜와 시간을 다룬다. 예) 2024-04-01T21:34:55+09:00[Asia/Seoul]
  - +9:00은 Asia/Seoul 타임존을 의미한다. UTC(협정 세계시)로 부터의 시간대 차이이다. 오프셋이라 한다.
  - Asia/Seoul은 타임존의 ID이다. 이것을 통해 타임존을 찾을 수 있다.
- OffsetDateTime: 시간대를 고려한 날짜와 시간을 표현할 때 사용한다. 여기에는 타임존은 없교 UTC로 부터의 시간대 차이인 고정된 오프셋만 포함한다.
    - 일광 절약 시간대가 적용되지 않는다.

# Instant
-  Instant는 타임스탬프를 표현한다. 타임스탬프는 1970년 1월 1일 0시 0분 0초(UTC)부터 현재까지의 시간을 나노초 단위로 표현한 것이다.

# Period와 Duration
시간의 개념은 크게 2가지로 표현활 수 있다.  
시간의 간격(기간)을 표현하는데 사용한다. 시간의 간격은 영어로 amount of time(시간의 양)으로 불린다.
- 특정 시점의 시간(시각)
- 시간의 간격(기간)
- Period : 두 날짜 사이의 간격을 년, 월, 일 단위로 나타낸다.
- Duration : 두 시간 사이의 간격을 초, 나노초 단위로 나타낸다.

# 날짜 비교
## 비교
- isBefore() : 다른 날짜시간과 비교한다. 현재 날짜와 시간이 이전이라면 true를 반환한다.
- isAfter() : 다른 날짜시간과 비교한다. 현재 날짜와 시간이 이후라면 true를 반환한다.
- isEqual() : 다른 날짜시간과 비교한다. 현재 날짜와 시간이 같다면 true를 반환한다.

## isEquals() vs equals()
- isEquals()는 단순히 비교 대상이  시간적으로 같은면 true를 반환한다. 객체가 다르고, 타임존이 달라도 시간적으로 같으면 True를 반환한다.
- 예) 서울의 9시와 UTCDML 0시는 시간적으로 같다. 이 둘을 비교하면 true를 반환한다.
- equals()는 두 객체가 같은지 비교한다. 객체가 다르면 false를 반환한다.

# 타임존 - ZoneDateTime
- "Asia/Seoul" 같은 타임존 안에는 일광 절약 시간제에 대한 정보와 UTC+9:00 같은 UTC 차이인 오프셋 정보를 모두 포함하고 있다.
- ZonedDateTime은 LocalDateTime에 시간대 정보인 ZoneId가 합쳐힌 것이다.

```java
OffsetDateTime nowOdt = OffsetDateTime.now();
System.out.println("nowOdt = " + nowOdt);
LocalDateTime ldt = LocalDateTime.of(2030, 1, 1, 13, 30, 50);
System.out.println("ldt = " + ldt);
OffsetDateTime odt = OffsetDateTime.of(ldt, ZoneOffset.of("+01:00"));
System.out.println("odt = " + odt);

nowOdt = 2024-04-25T17:59:41.633152+09:00
ldt = 2030-01-01T13:30:50
odt = 2030-01-01T13:30:50+01:00
```
ZoneOffset은 +01:00 같은 오프셋 정보를 제공한다.

### ZoneDateTime vs OffsetDateTime
- ZoneDateTime은 구체적인 지역 시간대를 다룰 때 사용하며, 일광 절약 시간을 자동으로 처리할 수 있다. 사용자 지정 시간대에 따른 시간 계산이 필요할 때 적합하다.
- OffsetDateTime은 UTC와의 시간 차이만을 나타낼때 사용하며 지역 시간대의 복잡성을 고려하지  않는다. 시간대 변환 없이 로그를 기록하고 데이터를 저장하고 처리할 때 적합하다.

# 기계 중심의 시간 - Instant
Instant는 UTC(협정 세계시)를 기준으로 하는, 시간의 한 지점을 나타낸다. Instant는 날짜와 시간을 나노초 정밀도로 표현하며, 1970년 1월 1일 0시 0초(UTC 기준)를 기준으로 경과한 시간으로 계산된다.  
쉽게 이야기해서 Instant 내부에는 초 데이터만 들어있다.(나노초 포함)  
따라서 날짜와 시간을 계산에 사용할 때는 적합하지 않다.

## Epoch 시간
Epoch time(에포크 시간) 또는 Unix timestamp는 컴퓨터 시스템에서 시간을 나타내는 방법 중 하나이다.  
이는 1970년 1월 1일 00:00:00 UTC 부터 현재까지 경과된 시간을 초 단위로 표현한 것이다.   
즉 Unix 시간은 1970년 1월 1일 이후로 경과한 전체 초의 수로 시간대에 영향을 받지 않는 절대적인 시간 표현 방식이다.  
Epoch라는 뜻은 어떤 중요한 사건이 발생한 시점을 기준으로 삼는 시작점을 뜻하는 용어다.  
Instant는 바로 이 Epoch 시간을 다루는 클래스이다.

## Instant 특징
- 장점
  - 시간대 독립성: Instant는 UTC를 기준으로 하므로 시간대에 영향을 받지 않는다. 이는 전 세계 어디서나 동일한 시점을 가리키는데 유용하다.
  - 고정된 기준점: 모든 Instant는 1970년 1월 1일 UTC를 기준으로 하기 때문에 시간 계산 및 비교가 명확하고 일관된다.
- 단점
  - 사용자 친화적이지 않음: Instant는 기계적인 시간 처리에는 적합하지만 사람이 읽고 이해하기에는 직관적이지 않다. 예를 들어 날짜와 시간을 계산하고 사용하는데 필요한 기능이 부족하다.
  - 시간대 정보 부재: Instant에는 시간대 정보가 포함되어 있지 않아, 특정 지역의 날짜와 시간으로 변환하려면 추가적인 작업이 필요하다.

일반적으로 날짜와 시간을 사용할 때는 LocalDateTime, ZonedDateTime 등을 사용하면 된다.

# 기간, 시간의 간격 - Duration, Period
시간의 개념은 크게 2가지로 표현할 수 있다.
- 특정 시점의 시간(시각)
  - 이 프로젝트는 2013년 8월 16일까지 완료해야된다.
  - 다음 저녁은 6시에 먹는다.
  - 내 생일은 5월 14일이다.
- 시간의 간격(기간)
  - 앞으로 4년은 더 공부해야 된다.
  - 이 프로젝트는 3개월 남았다.
  - 라면은 3분 동안 끓여야 된다.

Duration, Period는 시간의 간격(기간)을 표현하는데 사용된다.  
시간의 간격은 영어로 amount of time(시간의 양)으로 불린다.

## Period
두 날짜 사이의 간격을 년, 월, 일 단위로 나타낸다.
- 이 프로젝트는 3개월 정도 걸릴 것 같다.
- 기념일이 183일 남았다.
- 프로젝트 시작일과 종료일 사이의 간격: 프로젝트 기간

## Duration
두 시간 사이의 간격을 시, 분, 초(나노초) 단위로 나타낸다.
- 라면을 끓이는 시간은 3분이야
- 영화 상영 시간은 2시간 30분이야
- 서울에서 부산까지는 4시간이 걸려

|구분| Period| Duration|
|---|---|---|
|단위| 년, 월, 일| 시, 분, 초, 나노초|
| 사용대상|날짜|시간|
|주요 메소드| getYears(), getMonths(), getDays()| toHours(), toMinutes(), getSeconds(), getNano()|

# 날짜와 시간의 핵심 인터페이스
날짜와 시간은 특정 시점의 시간(시각) 과 시간의 간격(기간)으로 나눌 수 있다.  
- 특정 시점의 시간 : Temporal(TemporalAccessor 포함) 인터페이스를 구현한다.
  - 구현으로 LocalDateTime, LocalDate, LocalTime, ZonedDateTime, OffsetDateTime, Instant 등이 있다.
- 시간의 간격(기간) : TemporalAmount 인터페이스를 구현한다.
  - TemporalAmount를 구현한 클래스로는 Period, Duration이 있다.

## TemporalAccessor 인터페이스
- 날짜와 시간을 읽기 위한 기본 인터페이스
- 이 인터페이스는 특정 시점의 날짜와 시간 정보를 읽을 수 있는 최소한의 기능을 제공한다.

## Temporal 인터페이스
- TemporalAccessor의 하위 인터페이스로, 날짜와 시간을 조직(추가, 빼기 등) 하기 위한 기능을 제공한다. 이를 통해 날짜와 시간을 변경하거나 조정할 수 있다.

## TemporalAmount 인터페이스
시간의 간격(시간의 양, 기간)을 나타내며 날짜와 시간 객체에 적용하여 그 객체를 조정할 수 있다. 예를 들어, 특정 날짜에 일정 기간을 더하거나 빼는 데 사용 된다.

# 시간의 단위와 시간 필드
시간의 단위를 뜻하는 TemporalUnit(ChronUnit)과 시간의 각 필드를 뜻하는 TemporalField(ChronoField)이다.

## 시간의 단위- TemporalUnit, ChronoUnit
- TemporalUnit 인터페이스는 날짜와 시간을 측정하는 단위를 나타내며, 주로 사용되는 구현체는 java.time.temporal.ChronoUnit 열겨형으로 구현되어 있다.
- ChronoUnit은 다양한 시간 단위를 제공한다.
- 여기서 Unit이라는 뜻을 번역하면 단위이다. 따라서 시간의 단위 하나하나를 나타낸다.

### 시간 단위
|ChronoUnit|설명|
|---|---|
|NANOS|나노초|
|MICROS|마이크로초|
|MILLIS|밀리초|
|SECONDS|초|
|MINUTES|분|
|HOURS|시간|

### 날짜 단위
|ChronoUnit|설명|
|---|---|
|DAYS|일|
|WEEKS|주|
|MONTHS|월|
|YEARS|년|
|DECADES|십년|
|CENTURIES|백년|
|MILLENNIA|천년|

### 기타 단위
|ChronoUnit|설명|
|---|---|
|EARS|시대|
|FOREVER|영원|

### ChronoUnit의 주요 메서드
- between(Temporal, Temporal) : 두 Temporal 객체 사이의 시간 단위를 계산한다.
- isDateBased() : 현재 ChronoUnit이 날짜 기반 단위 인지(예: 일, 주, 월, 년) 여부를 반환한다.
- isTimeBased(): 현재 ChronoUnit이 시간 기반 단위인지(예: 시, 분, 초) 여부를 반환한다.
- isSupportedBy(Temporal): 주어진 Temporal 객체가 현재 ChronoUnit 단위를 지원하는지 여부를 반환한다.
- getDuration(): 현재 ChronoUnit의 기간을 Duration 객체로 반환한다.

##  시간 필드 - ChronoField
ChronoField는 날짜 및 시간을 나타내는데 사용되는 열겨형이다. 이 열거형은 다양한 필드를 통해 날짜와 시간의 특정 부분을 나타낸다. 여기에는 연도, 월, 일, 시간, 분 등이 포함된다.

- TemporalField 인터페이스는 날짜와 시간을 나타내는데 사용된다. 주로 사용되는 구현체는 ChronoField 열거형으로 구현되어 있다.
- ChronoField는 다양한 필드를 통해 날짜와 시간의 특정 부분을 나타낸다. 여기에는 연도, 월, 일 시간, 분 등이 포함된다.
- 여기서 필드(Field)라는 뜻이 날짜와 시간 중에 있는 특정 필드들을 뜻한다. 
- 예를 들어 2024년 4월 25일이라면 연도, 월, 일이 필드가 된다.
  - YEAR: 2024
  - MONTH_OF_YEAR: 4
  - DAY_OF_MONTH: 25
- 단순히 시간의 단위 하나하나를 뜻하는 ChronoUnit과는 다르다. ChronoField를 사용해야 날짜와 시간의 각 필드 중에 원하는 데이터를 조회할 수 있다.

### 연도 관련 필드
|필드 이름| 설명|
|---|---|
|ERA|연대, 예를 들어 서기(AD) 또는 기원전(BC)|
|YEAR_OF_ERA|연대 내의 연도|
|YEAR|년도|
|EPOCH_DAY| 1970-01-01부터의 일 수|

### 월 관련 필드
|필드 이름| 설명        |
|---|-----------|
|MONTH_OF_YEAR| 월(1월 = 1) |
|PROLEPTIC_MONTH|연도를 월로 확장한 값|

### 주 및 일 관련 필드
|필드 이름| 설명                     |
|---|------------------------|
|DAY_OF_WEEK| 요일(월요일 = 1)            |
|ALIGNED_DAY_OF_WEEK_IN_MONTH| 월의 첫번째 요일을 기준으로 정렬된 요일 |
|ALIGNED_DAY_OF_WEEK_IN_YEAR| 연도의 첫번째 요일을 기준으로 정렬된 요일 |
|DAY_OF_MONTH| 월의 일(1일 = 1)           |
|DAY_OF_YEAR| 연도의 일(1월 1일 = 1)      |
|EPOCH_DAY| 1970-01-01부터의 일 수       |

### 시간 관련 필드
|필드 이름| 설명        |
|---|-----------|
|HOUR_OF_DAY| 하루 중 시간(0-23) |
|CLOCK_HOUR_OF_DAY| 시계 시간(1-24) |
|HOUR_OF_AMPM| 오전/오후 시간(0-11) |
|CLOCK_HOUR_OF_AMPM| 오전/오후 시간(1-12) |
|MINUTE_OF_HOUR| 시간의 분(0-59) |
|SECOND_OF_MINUTE| 분의 초(0-59) |
|NANO_OF_SECOND| 초의 나노초(0-999,999,999) |
|MICRO_OF_SECOND| 초의 마이크로초(0-999,999) |
|MILLI_OF_SECOND| 초의 밀리초(0-999) |

### 기타 필드
|필드 이름| 설명        |
|---|-----------|
|AMPM_OF_DAY|하루의 AM 또는 PM|
|INSTANT_SECONDS|초를 기준으로 한 시간|
|OFFSET_SECONDS|UTC와의 차이를 초로 표현|


### 주요 메서드
|메서드| 반환 타입| 설명|
|---|---|---|
|getBaseUnit()| TemporalUnit| 필드의 기본 단위를 반환한다. 예를 들어, 분 필드의 기본 단위는 ChronoUnit.MINUTES이다.|
|getRangeUnit()|TemporalUnit| 필드의 범위 단위를 반환한다. 예를 들어, MONTH_OF_YEAR의 범위 단위는 CHRONOUNIT.YEARS이다.|
|isDateBased()|boolean| 필드가 날짜 기반 필드인지 여부를 반환한다.|
|isTimeBased()|boolean| 필드가 시간 기반 필드인지 여부를 반환한다.|
|ranged()|ValueRange|필드가 가질 수 있는 값의 유효 범위를 ValueRange 객체로 반환한다. 이 객체는 최소값과 최대값을 제공한다.|

TemporalUnit(ChronoUnit), TemporalField(ChronoField)는 단독으로 사용하기 보다는 주로 날짜와 시간을 조회하거나 조작할 때 사용한다.

# 날짜와 시간 조회하고 조작하기
날짜와 시간을 조회하려면 날짜와 시간 항목중에 어떤 필드를 조회할 지 선택해야 한다. 이때 날짜와 시간의 필드를 뜻하는 ChronoField가 사용된다.

## TemporalAccessor.get(TemporalField field)
- LocalDateTime을 포함한 특정 시점의 시간을 제공하는 클래스는 모두 TemporalAccessor 인터페이스를 구현한다.
- TemporalAccessor는 특정 시점의 시간을 조회하는 기능을 제공한다.
- get(TemporalField field)을 호출할 때 어떤 날짜와 시간 필드를 조회할지 TemporalField의 구현인 ChronoField를 인수로 전달하면 된다.

## Temporal plus(long amountToAdd, Temporalunit unit)
- LocalDateTime을 포함한 특정 시점의 시간을 제공하는 클래스 모두 Temporal 인터페이스를 구현한다.
- Temporal은 특정 시점의 시간을 조작하는 기능을 제공한다.
- plus(long amountToAdd, TemporalUnit unit)를 호출할 때 더하기 할 숫자와 시간의 단위(Unit)를 전달하면 된다.
- 이때 TemporalUnit의 구현인 ChronoUnit을 인수로 전달하면 된다.
- 불변이므로 반환 값을 받아야 한다.
- 참고로 minus()도 존재함.