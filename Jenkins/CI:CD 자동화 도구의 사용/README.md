# Jenkins를 이용한 CI/CD 자동화 도구의 사용

## Maven 플러그인 설치
Jenkins 관리 -> 플러그인 관리 -> Available plugins -> Maven IntegrationVersion
3.21 을 설치한다.

현재 도커쉘에서 mvn --version 입력후 command not found가 뜬다면 도커 환경에 메이븐이 설치가 안 된 상태 이므로 메이븐을 설치해야한다.  
Jenkins 관리 -> Global Tool Configuration -> Maven, Add Maven -> Maven3.9.0을 입력후 Save 한다.


# Maven 프로젝트 생성

새로운 아이템 -> Maven project -> 소스 코드 관리 Git 선택  

Repository URL  
https://github.com/jisu3316/cicd-web-project.git  


## Build 
Root POM : pom.xml  
Goals and options : clean compile package  

## Save > Build Now

```
/var/jenkins_home/workspace/My-second-Project/target
```
경로에 hello-world.war 가 생성된것을 확인할 수 있다.