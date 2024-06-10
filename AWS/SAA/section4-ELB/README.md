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