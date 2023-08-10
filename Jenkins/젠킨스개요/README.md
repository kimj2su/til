# 젠킨스 데이터파일 구조
Jenkins 는 현재 Jenkins 의 상태를 XML 형태로 직렬화 하여 ${JENKINS_HOME} 폴더에 기록한다.

## 루트폴더파일
config.xml
- jenkins 의 기본 설정을 관리하는 파일
- Jenkins 의 패스워드를 잃어버려서, 패스워드를 꺼놓고 작업해야 할 경우 유용  

{Hudson|jenkins}.*.xml 파일들  
  -플러그인별설정사항을저장하는파일

## workspace 폴더
JOB별 소스 코드를 체크아웃하고 빌드를 수행하는 폴더 - 별도로 설정하지 않으면, 기존 빌드 결과를 그대로 유지  
멀티 노드 환경에서는 workspace 폴더가 agent에 생성되어 빌드됨

## jobs 폴더
JOB 의 설정을 저장하고, 빌드 결과를 저장하는 폴더  
- config.xml 에 job 구성 사항이 저장되어 있음
- nextBuildNumber 에 다음 빌드 번호가 지정 되어 있음 빌드번호를 강제로 변환 시키고 싶다면 이 파일을 변경후, JENKINS 재시작
- builds 폴더에는 빌드 번호별 빌드 정보가 저장되어 있음 
  - log–빌드로그 
  - build.xml – 빌드 결과 
  - changelog.xml – 변경 내역 
  - archive – 빌드 결과를 아카이빙 설정했을때 아카이빙된 파일
- 디스크가 모자르다면 builds 에 너무 많은 데이터가 쌓여서 그런 경우가 많음. 따라서 builds 를 적절히 삭제해줘도 됨

## plugins 폴더
plugins 바이너리를 저장하는 폴더
- 젠킨스에서 플러그인(.jpi 또는 .hpi확장자)을 설치할 경우, 이 폴더에 저장되고 자동으로 압축이 풀림
- 동일한 버전의 Jenkins 를 다른 곳에서 실행한다면 이폴더를그대로복제하면플러그인을이동시킬수있음.
- 플러그인 문제로 인해 강제로 플러그인을 삭제해야 한다면 jpi 파일과 압축이 풀린 폴더 모두를 삭제해줘야 함 
  - 단 이경우, 해당 플러그인을 참조하고 있는 설정이 남아있다면 “ 다음 부팅시에 재난이 일어난다.

## userContent 폴더
스태틱 리소스를 제공하는 간단한 웹서버를 제공하는 폴더
- 해당 폴더에 파일을 위치시키면, http://{젠킨스호스트}/userContent/{파일명} 으로접근가능
- Jenkins 의 빌드 결과를 static resource 로 다시서버에제공하고자할때사용가능

## war / users / secrets 폴더
war 폴더
- Jenkins.war 의 압축을 풀어놓은 폴더 
 
users 폴더 
- 유저별이메일등의유저정보를보관하는폴더

secrets 폴더
- Jenkins 내부에서 사용하는 각종 secrets 들을 저장하는 폴더

# 플러그인 설치
## 플러그인 정보 검색 사이트
• https://plugins.jenkins.io/
## 플러그인 설치 메뉴 경로
• Dashboard > Jenkins 관리 -> Plugins > Available Plugins

