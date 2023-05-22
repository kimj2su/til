# 단위 테스트

작은 코드 단위를 독립적으로 검증하는 테스트 -> 클래스 or 메서드


# TDD (Test Driven Development)

1. Red -> 실패하는 테스트 작성
2. Green -> 테스트를 통과하는 프로덕션 코드 작성
3. Refactor -> 테스트를 통과하는 프로덕션 코드를 리팩토링

TDD의 가장 큰 장점은 피드백을 받을 수 있다는 것이다.
내가 작성한 코드에 대해 바로 성공/실패로 결과를 받을 수 있다.

선 기능 구현, 후 테스트 -> 테스트를 작성하지 않는다.
- 테스트 자체의 누락 가능성
- 특정 테스트 케이스만 검증할 가능성
- 잘못된 구현을 다소 늦게 발경할 가능성

선 테스트 작성, 후 기능 구현
- 복잡도가 낮은, 테스트 가능한 코드로 구현할 수 있게 한다. ex) createOrder
- 쉽게 발견하기 어려운 엣지(Edge) 케이스를 놓치지 않게 해준다.
- 구현에 대한 빠른 피드백을 받을 수 있다.
- 과감한 리랙토링이 가능해진다.

키워드 정리  
TDD, 레드-그린-리팩토링

애자일(Agile) 방법론 vs 폭포수 방법론  
익스트림 프로그래밍(XP, eXtreme Programming)  
스크럼(Scrum), 칸반(kanban)  


# 테스트는 문서다
- 프로덕션 기능을 설명하는 테스트 코드문서
- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
- 어느 한 사람이 과거에 경험 했던 고민의 결과물을 팀 차원으로 승격 시켜서, 모두의 자산으로 공유할 수 있다.

## DisplayName 을 섬세하게

1. 음료 1개 추가 테스트 (지양)
   - 명사의 나열보다 문장으로 - A이면 B이다. A이면 B가 아니고 C이다.
2. 음료 1개를 추가할 수 있다.
   - 테스트 행위에 대한 결과까지 기술하기
3. 특정 시간 이전에 주문을 생성하면 실패한다.
   - 테스트 케이스의 조건을 명확하게 기술하기
4. 영영 시작 시간 이전에는 주문을 생성할 수 없다.
    - 도메인 용어를 사용하여 한츨 추상화된 내용을 담기(메서드 자체의 관점보다 도메인 정책 관점으로)
    - 테스트의 현상을 중점으로 기술하지 말 것

## BDD, Behavior Driven Development
- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장

### Given, When, Then
- Given: 테스트를 위한 사전 조건 (객체, 값, 상태 등)
- When: 테스트를 수행하는 행위
- Then: 테스트를 통해 얻고자 하는 결과

어떤 환경에서(Given)
어떤 행위를 했을 때(When)
어떤 상태 변화가 일어난다.(Then)
-> DisplayName에 명확하게 잘성할 수 있다.

키워드 정리  
@DisplayName - 도메인 정책, 용어를 사용한 명확한 문장, TDD vs BDD, Given-When-Then - 주어진 환경, 행동 ,상태 변화

JUnit vs Spock

# Layered Architecture

## Persistence Layer - Repository test
- Data Access의 역할
- 비즈니스 가공 로직이 포함되어서는 안된다. Data에 대한 CRUD에만 집중한 레이어

## Business Layer - Service test(Persistence Layer 포함)
- 비즈니스 로직을 구현하는 역할
- Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)을 통해 비즈니스 로직을 전개시킨다.
- 트랜잭션을 보장해야 한다.

## Presentation Layer - Controller test(Business Layer 포함)
- 외부 세계의 요청을 가장 먼저 받는 계층
- 파라미터에 대한 최소한의 검증을 수행한다.

# Test Fixture 클렌징
## deleteAll() vs deleteAllInBatch()

deleteAll은 table을 조회해서 where절에 id를 찾아 다 삭제하지만 deleteAllInBatch는
where절 없이 테이블을 다 지우기 때문에 성능차이가 있다.














