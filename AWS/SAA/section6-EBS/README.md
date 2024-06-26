# Elastic Block Store (EBS)
- EC2에 연결하여 사용하는 블록 스토리지
- EC2 인스턴스 시작 시 AMI가 설치 되는 EBS 루트(부트) 볼륨이 생성됨
- 여러 개의 EBS 볼륨을 생성하여 EC2에 추가 연결 가능
- EBS와 EC2는 동일한 가용영역에 있어야 연결 가능
- 스냅샷(Snapshot) 기능을 통해 EBS 볼륨 백업 가능
- 수명 주기 관리자 (Data Life Cycle Manage) 정책을 통해 스냅샷 생성 일정을 자동화 가능
- AWS Key Management Service(KMS)를 이용해 EBS 볼륨 암호화 가능

## EBS 볼륨 유형
- 볼륨 유형에 따라 제공되는 용량(크기), IOPS, 최대 처리량이 다름
- 부트 볼륨은 범용 SSD, 프로비저닝 된 SSD 만 지원
- EBS 다중 연결은 프로비저닝 된 SSD만 지원

## EBS 다중 연결(EBS Multi-Attach)
- 하나의 EBS 볼륨을 여러 EC2에  동시에 연결 하는 기능
- 동일한 가용영역 내에서만 연결 가능
- 모든 EC2 유형이 연결 가능하지 않고 Nitro기반의 Linuix 인스턴스만 연결 가능
- 모든 EBS 볼륨 유형이 연결 가능하지 않고 프로비저닝 된 IOPS SSd(Provisioned IOPS SSD)만 지원
- 동시에 최대 16대의 EC2 인스턴스 연결 가능
- 여러 EC2 인스턴스에서 하나의 EBS 볼륨에 동시 쓰기 작업이 필요한 클러스터링 된 Linux 애플리케이션에서 사용

## EBS 스냅샷
- EBS 볼륨의 데이터를 백업 하는 기능
- 백업된 스냅샷을 다른 가용영역 또는 리전에 복사 가능
- 스냅샷을 커스텀 AMI 이미지로 만들 수 있음
- 백업된 스냅샷을 가지고 새로운 EC2 인스턴스를 생성 가능
- EBS 스냅샷 아카이브(EBS Snapshot Archive)
  - 자주 액세스 하지 않는 스냅샷을 저렴한 아카이브 스토리지 계층에 보관
  - 90일 이상 저장할 계회이고 액세스할 필요가 거의 없는 스냅샷에 대해 최대 75% 비용이 저렴(최소 과금 기간 90일)
- EBS 스냅샷 휴지통(Recycle Bin for EBS Snapshot)
  - 실수로 삭제한 스냅샷을 휴지통에 보관해서 복구 가능
  - 삭제된 파일을 1일 부터 365일 까지 보관 성정 가능
- EBS 빠른 스냅샷 복원(EBS Fast Restore-FSR)
  - 지연시간을 최소화 하여 빠르게 스냅샷으로부터 EBS 볼륨을 복원하는 기능

## 암호화 되지 않은 EBS 볼륨 암호화
1. EBS 볼륨 스냅샷을 생성
2. EBS 볼륨 스냅샷을 복사한 후 스냅샷을 암호화 후 암호화된 스냅샷으로부터 새 EBS 볼륨 생성
3. 새 EBS 볼륨을 EC2인스턴스에 연결

## Instance Store
 - 블록 수준의 임시 스토리지
 - 특정 인스턴스 유형은 Instance Store라고 하는 스토리지를 가지고 있음
 - Instance Store는 서버에 직접 장착되어 있는 물리적 SSD 또는 HDD스토리지
 - Instance Store는 IOPS 성능이 매우 높은 고성능 스토리지
 - 인스턴스를 중지하거나 최대 절전 모드로 전환하거나 종료하면 인스턴스 스토어의 모든 스토리지 블록이 리셋 
 - 임시 파일을 보관하는 가장 빠른 성능의 저장 옵션
 - Instance Store는 EC2인스턴스가 종료되면 데이터가 삭제되므로 영구적인 저장소가 아닌 고성능을 구하는 애플리케이션의 임시 저장소로 적합 
 - 예, 초당 수백 만개의 트랜잭션을 지원하는 I/O처리량이 높은 데이터 베이스의 데이터의 임시 스토리지 옵션으로 사용
- 중요한 장기 데이터의 경우 Instance Store가 아닌 Amazon S3, Amazon EBS, Amazon EFS 등의 데이터 스토리지를 사용

## Elastic File System(EFS)
- 리눅스 환경의 EC2 인스턴스에서 연결하기 위한 네트워크 파일 스토리지
- Network File System(NFS)프로토콜을 지원
- 여러 가용영역에 있는 수십~수백대의 EC2 연결 가능(고 가용성, 확장성)
- -EFS는 보안그룹을 통해 인스턴스에 연결
- EC2외에 Linux방식의 온-프레미스 서버에서도 연결 가능
- AWS Key Management Service(KMS)를 이용해 EFS 암호화 가능

## Elastic File System(EFS) - 스토리지 클래스
- 스토리지 클래스
- 표준 스토리지 (Standard) - 3개의 가용영역에 데이터 저장, 자주 액세스하는 파일을 저장하는데 사용
- 표준 IA (Standard Infrequent Access) - 3개의 가용영역에 데이터 저장, 자주 액세스하지 않는 파일을 저장하는 데 사용
- One Zone - 1개의 가용영역에 데이터 저장, 자주 액세스하는 파일을 저장하는 데 사용
- One Zone IA (One Zone Infrequent Access) - 1개의 가용영역에 데이터 저장, 자주 액세스하지 않는 파일을 저장하는 데 사용
- EFS 수명주기 관리 정책 - 일정 기간 동안 액세스하지 않은 파일을 액세스 빈도가 낮은 스토리지 클래스인 Standard-IA 또 는 One Zone-IA로 이동하여 비용 절감
- EFS 지능형 계층화(Intelligent-Tiering) - 워크로드의 액세스 패턴을 모니터링하고 해당 표준 스토리지 클래스 (EFS Standard 또는 EFS One Zone) 에서 일정 기간 (예: 30일) 동안 액세스되지 않는 파일은 해당하는 Infrequent Access (IA)스토리지 클래스로 자동전환

## Elastic File System (EFS) – 성능
- 파일 시스템 성능은 일반적으로 지연 시간, 처리량, 초당 입/출력 작업 수 (IOPS) 의 차원을 사용하여 측정
  - 성능 모드 : I/O, 읽기 쓰기 속도
   - 기본 범용 성능 모드 (General Purpose performance mode) – 일반적인 I/O 성능
  - 최대 I/O 성능 모드 (Max I/O performance mode) – 높은 성능의 처리가 필요한 빅데이터 분석 애플리케이션 등에 사용
- 처리량 모드 : 파일 시스템의 처리량(MiB/s)
  - 탄력적 처리량 모드
    - 자동으로 처리량을 늘리거나 줄임
    - 성능 요구 사항을 예측하기 어려운, 변동이 심하거나 예측할 수 없는 워크로드에 적합
  - 프로비저닝 처리량 모드
    - 사용자가 직접 고정으로 처리량을 지정
    - 워크로드의 성능 요구 사항을 알고 있는 경우 적합
  - 버스팅 처리량 모드
    - 스토리지 파일 용량에 따라 처리량이 결정됨
    - 스토리지 용량에 따라 처리량이 확장되어야 하는 워크로드에 적합