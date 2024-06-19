# EC2 Auto Scaling
- 애플리케이션 수요에 따라 EC2 인스턴스를 자동으로 확장하고 축소하는 기능
- 사용자가 정의한 조정 정책에 따라 인스턴스 수가 증가 되거나 축소 됨
  - 예, 서버의 로드가 증가하면 EC2 인스턴스 개수 추가(Scale Out)
  - 예, 서버의 로드가 감소하면 Ec2 인스턴스 개수 감소(Scale In)

## EC2 Auto Scaling 이점
- 애플리케이션의 수요에 EC2를 자동으로 확장 및 축소하므로 비용 절감 및 수동으로 EC2 용량을 프로비저닝해야 할 필요가 없음
- 손상된 Amazon EC2 인스턴스를 탐지하고 Auto Scaling 이 자동으로 교체
- 여러 가용 영역을 사용하도록 EC2 Auto Scaling을 구성 하여 하나의 가용 영역이 사용 불가 상태가 되면 다른 가용 영역에서 새 인스턴스를 시작

## EC2 Auto Scaling 구성 요소
- 오토 스케일링 그룹 : EC2 인스턴스의 모음
- 시작 템플릿(런치 템플릿) : EC2 인스턴스를 시작하기 위한 AMI, 인스턴스 유형, 키 페어, 보안 그룹 등의 구성 정보를 가진 템플릿
- 조정 옵션(조정 정책) : Auto Scaling을 조정하는 조건

## EC2 Auto Scaling - 조정 정책
- 항상 현재 인스턴스 수준 유지 관리
  - 지정된 수의 실행 인스턴스를 항상 유지하도록 Auto Scaling 그룹을 구성
  - 인스턴스가 비정상 상태임을 확인하면 해당 인스턴스를 종료한 다음 새 인스턴스를 시작
- 수동 조정
  - 최대, 최소 또는 원하는 용량의 변경 사항만 지정하는 경우 사용
- 일정을 기반으로 조정(Sceduled Scling)
  - 확장 작업이 시간 및 날짜 함수에 따라 자동으로 수행됨
  - 예, 매주 일요일에는 인스턴스 4대, 다른 요일에는 2대 실행
- 온디맨드 기반 조정(Dynamic Scaling)
  - 수요 변화에 맞춰 Auto Scaling 그룹의 크기를 동적으로 조정
  - 예, CPU 사용량을 50%기준으로 하고 사용량 기준에 따라 EC2 인스턴스 수를 증가하거나 감소
- 예측 조정 사용(PPredictive Scling)
  - 머신 러닝을 사용하여 CloudWatch의 기록 데이터를 기반으로 용량 필요량을 예측

## EC2 Auto Scaling - 동적 조정(Dynamic Scaling)
- 대상 추적 조정(Target Tracking Scaling)
    - 지정한 지표 유형의 대상값을 기준으로 Auto Scaling 그룹을 조정 하는 방식
    - 지표유형: 평균 CPU 사용률, 네트워크 인터페이스에서 송/수신한 평균 바이트 수, 로드발란서 요청 수
    - 예, 평균 CPU 사용률을 50%로 설정하면 Auto Scaling 그룹이 목표 값에 따라 EC2 인스턴스 증가 및 감소를 통해 지표 값을 50%에 가깝게 유지
- 단계 조정(Step Scaling)
  - CloudWatch alarm의 지표를 기반으로 Auto Scaling 그룹을 확장 하는 방식
  -  예, CPU 사용률이 60% 초과하면 Auto Scaling Group 10% 또는 2개 증가 CPU 사용률이 30% 이하면 Auto Scaling Group 10% 또는 2개 감소
  - 크기 조정 활동 또는 상태 확인 교체가 진행 중인 동안에도 정책이 추가 경보에 계속 응답
- 단순 조정(Simple Scaling)
  - CloudWatch alarm의 지표를 기반으로 Auto Scaling 그룹을 확장 하는 방식
  - 예, CPU 사용률이 60% 초과하면 Auto Scaling Group 10% 또는 2개 증가 CPU 사용률이 30% 이하면 Auto Scaling Group 10% 또는 2개 감소
  - 크기 조정 활동이 시작된 후 정책은 크기 조정 활동 또는 상태 확인 교체가 완료되고 휴지 기간(Cooldown Period)이 끝날 때까지 기다린 후 추가 경보에 응답
- Amazon SQS 기반 크기 조정
  - Amazon SQS 대기열의 시스템 로드 변경에 따라 Auto Scaling 그룹을 조정