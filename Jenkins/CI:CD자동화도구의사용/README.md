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

<br/><br/>
# PollSCM 설정을 통한 지속적인 파일 업데이트 (폴링)

## cron  
소프트웨어 유틸리티 cron은 유닉스 계열 컴퓨터 운영 체제의 시간 기반 잡 스케줄러이다. 소프트웨어 환경을 설정하고 관리하는 사람들은 작업을 고정된 시간, 날짜, 간격에 주기적으로 실행할 수 있도록 스케줄링하기 위해 cron을 사용한다.  -위키백과-  

Project -> Configure -> Build Triggers 
- Build periodically -> cron job 
- Poll SCM -> cron job
첫번째 크론잡은 코드의 변경사항이 없어도 일단 빌드를 다시 합니다.  두번째 크론잡은 깃허브에서 코드를 가져올 때 커밋에 대한 내용이 있을 경우만 빌드를 하게 됩니다.

## 빌드 유발
Poll SCM - Schedule 
```
* * * * *
```
이렇게 추가하고 git 에 commit, push를 하면 빌드가 자동으로 된다.


<br/><br/>

# SSH Server 설치
```
docker pull edowon0623/docker-server:m1
```
위 명령어로 이미지를 다운 받는다.  
docker images -> 이미지 확인

```
 docker run --privileged --name docker-server -itd -p 10022:22 -p 8081:8080 -e container=docker -v /sys/fs/cgroup:/sys/fs/cgroup:rw --cgroupns=host edowon0623/docker-server:m1 /usr/sbin/init
 ```

 ## SSH 서버 접속
 ssh root@localhost -p 10022   
 Are you sure you want to continue connecting (yes/no/[fingerprint])? yes  
 root@localhost's password:   

## docker 서버 작동 확인
```
 systemctl status docker
  Loaded: loaded (/usr/lib/systemd/system/docker.service; disabled; vendor preset: disabled)
   Active: inactive (dead)
     Docs: https://docs.docker.com
```
inactive면 작동 안 된 상태이다.     

작동중인 상태로 만들어주려면 다음과 같은 명령어를 입력해주면 된다.
```
systemctl enable docker

결과
Created symlink /etc/systemd/system/multi-user.target.wants/docker.service → /usr/lib/systemd/system/docker.service.


systemctl start docker

```

만약 여기서 오류가 난다면 설정을 변경해줘야한다.  
$ vi /etc/sysconfig/docker  
$ yum install -y iptables net-tools  
$ sed -i -e 's/overlay2/vfs/g' /etc/sysconfig/docker-storage  
$ systemctl start docker  