# Jenkins Jon 타입



|구분  |설명| 사용예                          |
|------|---|------------------------------|
|FreeStyle project|UI를통해자유롭게빌드설정| • 간단한빌드/배포JOB구성 <br/>• 배치 트리거로 사용 |
|Pipeline|스크립트(jenkinsfile) 을 사용하여 빌드 실행| • github repo 에 jenkins 파일을 위치시키고 빌드 수행 최근 실무에서 가장 많이 사용되는 방식                         |
|Multi Configuration project|다양한조건을조합을조합하여,반복빌드실행| • 다양한환경조건하에서테스트실행                         |
|Folder|Job들을 관리하는 폴더|                          |
|Multi Branch Pipeline|scm 의 여러 브랜치들을 동시에 pipeline 빌드 | • 다양한 브랜치를 동시 운영하는 경우(PR 포함)                          |
|Organization Folder|github organization 하위의 repo들을 동시 빌드| • Multi Branch Pipeline 을 github organization 레벨로 확장                         |


## FreeStyle Job
|제목| 내용                            |설명|
|------|-------------------------------|---|
|소스코드관리| 어떤 코드를 감지하고 어떻게 체크아웃 할 것인지 지정 |• A github repo 를 hello/ 폴더에 체크아웃 • Bgithubrepo중src폴더만체크아웃|
|빌드 유발| 빌드를 시작하는 방법을 지정                          |•월요일 밤 12시에 시작<br/>•매1분에한번씩코드REPO를체크하여,변경이있으면빌드시작<br/>•다른 JOB이 빌드 완료되면 빌드 시작<br/>• 원격에서 API 호출로 빌드 시작|
|빌드 환경| 빌드하기전필요한설정지정                          |• 빌드 시작 전 workspace 삭제|
|빌드 스텝| 실제 빌드 스크립트 실행                          |• 빌드명령실행<br/>• 소스코드빌드<br/>• docker 이미지 생성<br/>• SonarScanner 실행<br/>• ArgoCd 실행|
|빌드후 조치| 빌드 이후에 빌드 결과물을 처리                          |• Artifact 아카이빙<br/>• 테스트결과/테스트커버리지등가시화<br/>• Slack / 이메일로 빌드 결과 전송<br/>• 다른 Job 실행|

## Additional Behavioures
Sparse Checkout paths - 옵션을 사용하면 체크아웃시 원하는 파일만 체크아웃 할 수 있습니다.  
또한 sub-directory를 통해 폴더를 나눌 수 있다.

# 빌드 유발
빌드시작방법지정
- 주기적빌드
- 주기적Polling을통한변경검출후빌드 
- 외부 이벤트에 의한 빌드 
  - API를사용한빌드 
  - WEBHOOK을사용한빌드

빌드 시작이 특정 시간에 진행되고 많은 JENKINS 가 사용될 경우, SCM 에 부하가 몰리는 이슈가 있음.

Github 를 사용할 경우, Github Hook 을 통한 빌드 유발을 사용하는게 일반적


## Build periodically
cron tab을 사용한다.

# 빌드 스텝
빌드절차지정
- 각종 Shell / Batch 실행
- Gradle / Maven / Ant task 실행 - 다른Job빌드시작
- 플러그인을 통한 액션 수행  

다수개의 빌드 스텝을 설정 가능 단앞빌드스텝실패시 다음 빌드 스텝을 실행하지 않음

# 빌드 결과 slack 전송
개발자는 빌드/배포결과를통보받아,에러발생시에조치를취해야함 Email Notification 플러그인을 활용 빌드 결과를 이메일을 통해 전송
- SMTP 프로토콜 사용
- E-mail Notification 플러그인을 사용하여 FAIL / UNSTABLE 간편하게 전송
  가능
- Editable Email Notification 플러그인을 사용하면 좀 더 상세하게 전송
  커스터마이징

Slack Notification 플러그인을 활용하여 빌드 결과를 Slack 으로 전송
- Slack 의 App Integration 기능을 활용
- Slack 플러그인 추가

• 플러그인명 : Slack 플러그인  
Slack API 연동을 통해, 빌드 진행 사항과 결과를 Slack 채널에 통지  
• 설치후 Jenkins 관리 > System > Slack

Dashboard > [JOB명] > 구성 > 빌드후 조치 > Slack Notification  