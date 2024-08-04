# AWS Storage Gateway
## Storage Gateway
- 온-프레미스 데이터 센터의 데이터와 AWS 클라우드의 스토리지를 연결하는 서비스
- 하이브리드 클라우드 스토리지 서비스(온-프레미스 인프라 + 클라우드 인프라)
- AWS 데이터 백업, AWS 클라우드 스토리지로 파일공유, 제헤 복구 저장소, 온-프레미스에서 AWS 클라우드 데이터 액세스 등의 용도로 사용
- 4가지 유형의 게이트웨이: S3 파일 게이트웨ㅣ, FSx 파일 게이트웨어, 볼륨게이트웨이, 테이프게이트웨이
- 파일, 테이프, 볼륨 게이트웨이를 통해 NFS,SMB,ISCSI 및 ISCSI-VTL 인터페이스를 사용 가능
- 온-프레미스에서 Storage Gateway 소프트웨어가 포함된 가상 머신을 VMware ESXi, Microsoft Hyper-V 또는 Linux KVM에 배포하거나 Storage Gateway를 하드웨어 어플라이언스로 배포, 또한 Storage Gateway VM을
VMware Cloud on AWS에 배포하거나 Amazon EC2에서 AMI로 배포
- 온-프레미스 로컬캐시 기능을 통해 자주 사용하는 데이터를 빠르게 액세스 가능
## S3 파일 게이트웨이
- 온-프레미스와 S3간에 파일 기반 인터페이스를 제공하는 게이트웨이
- NFS, SMB 파일 공유 프로토콜을 사용하여 Amazon S3에 객체를 저장하고 검색 가능
- Active Directory(AD) 서비스와 통합하여 인증된 사용자만 액세스 하도록 구성 가능
- 온 - 프레미스의 파일 저장공간이 부족하여 파일을 클라우드에 저장해서 액세스하는 경우 또는 파일을 백업 용도로 클라우드에 젖아하는데 사용

## FSx 파일 게이트웨이
- 온-프레미스와 FSx for Windows File Server간에 파일 기반 인터페이스를 제공하는 게이트웨이
- SMB 프로토콜을 사용하여 FSx for Windows File Server 내의 파일을 저장하고 검색 가능
- Active Directory(AD) 서비스와 통합하여 인증된 사용자만 액세스 하도록 구성 가능

## Storage Gateway 볼륨 게이트웨이
- 온-프레미스와 S3가넹 ISCSI 블록 스토리지 기반 인터페이스를 제공하는 게이트웨이
- 온-프레미스의 서버에서 S3의 블록 스토리지 볼륨을 iSCSI 디바이스로 연결 가능
- 보륨 데이터는 S3에 저장되며 볼륨을 EBS 스냅샷으로 저장 및 AWS Backup 서비스로 백업 가능
- 두가지 볼륨 모드
  - 캐시 볼륨 모드: S3에 저장된 데이터를 로컬 캐시에 저장하고 자주 사용하는 데이터에 대한 빠른 액세스 가능
  - 저장 볼륨 : 모든 데이터를 로컬에 저장 후 AWS 비동기식 백업, 로컬에 백업세트가 저장된 효과

## Storage Gateway 테이프 게이트웨이
- 온-프레미스와 S3간에 iSCSI VTL(가상 테이프 라이브러리) 인터페이스를 제공하는 게이트웨이
- 온-프레미스의 테이프 백업 애플리케이션 서버와 S3의 가상테이프간의 데이터 전송
- 기존의 온-프레미스 테이프 백업 장치 구성을 변경하지 않아도 AWS S3로 백업 가능
- 로컬에 테이프 백업을 보관하지 않고 지리적으로 분리된 AWS 클라우드에 보관하기에 고가용성, 비용절감, 내구성 등의 장점이 있음
- S3 Glacier(S3 Glacier Flexible Retrieval) 또는 S3 Glacier Deep Archive로 백업을 하여 저장 비용 최소화 가능

## Storage Gateway - 하드웨어 어플라이언스
- 스토리지 게이트 웨이 운영을 위해서는 온프레미스 서버에 Storage Gateway 애플리케이션을 설치해야함
- 온프레미스에 서버 등의 저장장치가 없거나 인프라가 부족한 작은 데이터 센터의 경우 Storage Gateway 소프트웨어가 미리 설치된 하드웨어 어플라이언스를 구매하여 운용 가능


## Amazon FSx
- Amazon 파일 시스템
- 온-프레미스와 AWS 클라우드에서 액세스 가능
- 4가지 유형의 파일 시스템 제공
- FSx for Lustre
  - 리눅스 환경을 위한 고성능 병렬 스토리지 시스템
  - 병렬 스토리지 시스템 = 분산파일 시스템(DFS, Distributed File System)
  - 머신 러닝, 빅데이터 등의 고성능 컴퓨팅에 사용 (HPC, High Performance Computing)
  - Amazon S3 버킷과 통합 구성하여 같이 사용가능
  - 파일 시스템 배포 옵션
    - 스크래치 파일 시스템
    - 지속적 파일 시스템