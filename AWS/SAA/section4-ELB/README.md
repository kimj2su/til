# Elastic Load Balancing(ELB)
## 고가용성과 확장성
### 확장성(Scalability)
- 수직적 확장
  - 자원을 추가하는 방식
  - 예, EC2 인스턴스유형을 t2.mirco에서 t2.large로 변경하는 것
- 수평적 확장
  - 노드를 추가하는 방식
  - EC2 인스턴스 개수를 늘리는 것
  - 사용량에 따라 즉시 리소스를 확장,  축소를 할 수 있기에 탄력성이 있음
  - 애플리케이션의 확장 방법으로 주로 사용됨

### 고가용성 (High Availability) vs 내결함성(Fault Tolerance)
- 고가용성
  - 서비스 중단을 최소화
  - 애플리케이션이 99.99% 실행된느 것을 목표
  - AWS에서 고가용성은 2개 이상의 가용 영역에 애플리케이션이나 리소스를 운영하는 것
  - 내결함성 설계보다 덜 복잡하고 비용이 상대적으로 적게 발생
- 내결함성
  - 서비스 중단이 없음
  - 애플리케이션을 계속 실행하는 것을 목표(zero downtime)
  - 구성요소에 오류가 발생해도 계속 작동하도록 중복 하드웨어 구성
  - 중복된 리소스 구성으로 구성이 복잡하고 비용이 많이 발생

## Elastic Load Balancing(ELB) 개요
- 로드 밸런서는 네트워크 트래픽을 분산하는 서비스이다.
- Elastic Load Balancer
  - AWS 로드발란서 서비스
  - 네트워크 트래픽을 EC2 인스턴스, 컨테이너 IP주소 등 여러 대상으로 자동으로 분산 가능
  - 애플리케이션의 가용성과 내구성을 높일 숭 ㅣㅆ음
  - 로드발란서가 비정상 대상을 감지하면 해당 대상으로 트래픽 라우팅을 중단하고 정상 대상으로만 트래픽 라우팅, 대상이 다시 정상으로 감지되면 트래픽을 해당 대상으로 다시 라우팅 가능

### Elastic Load Balancer 종류
- Application Load Balancer(ALB)
  - Layer 7
  - HTTP, HTTPS
  - HTTP Header Content를 사용해 라우팅 요청 처리
  - 웹 애플리케이션 서비스에 적립
- Network Load Balancer(NLB)
  - Layer 4
  - TCP, UDP, TLS
  - Protocol, Port Number를 사용해 라우팅 요청 처리
  - 수백만의 대용량 트래픽 처리에 적합
- Gateway Load Balancer
  - Layer 3 - Gateway Load Balancer Endpoint
  - Layer 4 - Gateway Load Balancer
  - GENEVE protocol을 사용하여 encapsulation 트래픽 전송
  - Transparency한 네트워크 게이트웨이를 제공하므로 보안 검사를 위한 방화벽, IPS, IDS 등의 원본 패킷의 데이터가 중요한 가상 어플라이언스에 적합
- Classic Load Balancer
  - Layer 4, Layer 7
  - HTTP, HTTPS, TCP
  - ALB, NLB로 대체 권장
  - 기존에 사용하던 ELB

## Elastic Load Balancer 구성
- Listener
  - 구성한 프로토콜 및 포트를 사용해 연결 요청을 확인하는 기능
  - 리스너에서 정의한 규칙에 따라 로드밸러서가 대상 그룹에서 대상으로 라우팅하는 방법이 결정됨
- Target Group
  - 대상(Target)의 모임
  - Target
    - EC2 인스턴스
    - EC2 Auto Scaling 그룹
    - IP address
    - Lambda
    - Application Load Balancer

## Target Groups 개요 및 실습
### 대상 유형 (Target Type)
- 인스턴스 : 개별 EC2 Instance, EC2 Auto Scaling Groups
- Lambda 함수 : Application Load Balancer만 연결 가능
- Application Load Balancer : Network Load Balancer만 연결 가능

### Protocol
- HTTP, HTTPS -> ALB
- TCP, TLS, UDP, TCP_UDP-> NLB
- GENEVE -> GWLB

### Health Check
등록된 Target(대상)에게 상태 확인 메시지를 보내서 대상의 상태를 확인

### 속성(Attributes) - HTTP/HTTPS
- Application Load Balancer에서 사용
- 등록 취소 지연(Deregistration delay/Connecting Draining)
  - Auto Scaling 축소 등으로 등록취소 된 인스턴스에 더 이상의 요청을 보내지 않도록 하는 기능
  - 해당 인스턴스에 진행중이 요청이 있을 경우 설정해 놓은 시 간동안 연결이 유효상태가 되지 않으면 해당 인스턴스에 연 결 요청을 하지 않음
- 느린 시작 기간(Slow start duration)
  - 기본적으로 대상은 대상 그룹으로 등록되자 마자 전체 요청 공유를 받기 시작하고 초기 상태 확인을 전달
  - 느린 시작 모드에서는 로드 밸런서가 대상으로 보낼 수 있는 요청의 수를 선형으로 증가
- 알고리즘
  • 라운드 로빈(Round-Robin): 일정 시간마다 라우팅을 변경
  • 최소 미해결 요청(Least Outstanding Requests): 처리하고 있는 요청이 가장 적은 대상에게 라우팅
- 고정(Stickiness Sessions / Session Affinity)
  -  클라이언트가 세션을 유지한 상태라면 모든 요청을 동일한 인스턴스로 유지하는 기능
  - 세션 데이터를 잃지 않으려는 상태정보를 유지하는 서버에 적합

### 속성(Attributes) - TCP/UDP/TLS
-Network Load Balancer 에서 사용
- 등록 취소 지연(Deregistration delay)
    - Auto Scaling 축소 등으로 등록 취소된 인스턴스에 더 이상의 요청을 보내지 않도록 하는 기능
      - 해당 인스턴스에 진행중이 요청이 있을 경우 설정해 놓은 시 간동안 연결이 유효상태가 되지 않으면 해당 인스턴스에 연결 요청을 하지 않음
- 등록 취소시 연결 종료 (Connection termination on deregistration)
  - 등록 취소 지연에 도달 했을 때 NLB가 활성 연결 종료
- 프록시 프로토콜 v2
- 클라이언트 IP 주소 보존 (Preserve client IP addresses)
  - 들어오는 모든 트래픽의 클라이언트 IP를 애플리케이션으로 전달
- 고정(Stickiness Sessions / Session Affinity)
  - 클라이언트가 세션을 유지한 상태라면 모든 요청을 동일한 인스턴스로 유지하는 기능
  - 세션 데이터를 잃지 않으려는 상태정보를 유지하는 서버에 적합

### 고정 세션 (Stickiness Sessions / Session Affinity)
- 고정 세션은 대상 그룹의 동일한 대상으로 클라이언트 트래픽을 라우팅하는 기술
- Application Load Balancer, Network Load Balancer 모두 사용 가능
- Application Load Balancer는 세션 유지를 위해 쿠키(cookie)를 사용하기에 클라이언트 쿠키를 지원해야 함

### 교차 영역 로드 밸런싱 (Cross-Zone Load Balancing)
- 교차 영역 로드 밸런싱 비활성화
  - 가용영역 내에 있는 타겟에만 트래픽을 분배
- 교차 영역 로드 밸런싱 활성화
  - 모든 가용영역의 등록된 모든 탁겟 인스턴스에 동일하게 트래픽을 분배