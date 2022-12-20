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
<br/><br/><br/>
# CI/CD 작업을 위한 Tomcat 서버 연동

Jenkins 관리 -> 플러그인 관리 -> Available plugins -> Deploy to container

이 플러그인이 있어서 앞에서 패킹징해서 만든 war를 파일을 톰캣 서버에 복사해 넣을 수 있습니다.  

## 새로운 아이템 -> Maven project -> My-Third-Project

Repository URL  
https://github.com/jisu3316/cicd-web-project.git  

## Build 
Root POM : pom.xml  
Goals and options : clean compile package  

# Post-build Actions  
빌드 후 조치  
위에서 war파일로 압축까지 된것까지는 확인했습니다.  
생선된 파일을 가지고 (빌드된 뒤에 어떤 작업을 할 것인가)를 정해주는 항목입니다.  

## Deploy war/ear to a container  
### **/*.war   
파일의 이름은 현재 디렉토리, 하위 디렉토리를 포함해서 .war 확장자를 가지고 있는 파일이 대상이라는 뜻입니다.  

## Container : Tomcat 9.x Remote  
위에서 만든 war파일을 배포하기 위해서는 접근하기 위한 권한이 있어야 합니다.  
그래서 Credentials을 추가해야합니다.  

# Tomcat 설치
https://tomcat.apache.org  
9.x 버전 사용

# Tomcat 시작
- Windows) .\bin\startup.bat 
- MacOS) ./bin/startup.sh
# Tomcat 종료
- Windows) .\bin\shutdown.bat 
- MacOS) ./bin/shutdown.sh
# Tomcat 설정 변경
## 포트 변경 
- %TOMCAT_HOME%\conf\server.xml
## 접근 엑세스 변경 
- %TOMCAT_HOME%\webapps\manager\META-INF\context.xml 
- %TOMCAT_HOME%\webapps\host-manager\META-INF\context.xml 
## 유저 추가
- %TOMCAT_HOME%\conf\tomcat-users.xml
- tomcat-users.xml 추가 내용
 
```
<role rolename="manager-gui" />
<role rolename="manager-script" />
<role rolename="manager-jmx" />
<role rolename="manager-status" />
<user username="admin" password="admin"  roles="manager-gui, manager-script, manager-jmx, manager-status"/>
<user username="deployer" password="deployer" roles="manager-script"/>
<user username="tomcat" password="tomcat"  roles="manager-gui"/>
```