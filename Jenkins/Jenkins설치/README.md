# Jenkins

- 지속적인 통합과 배포(CI/CD) -> Work flow를 제어
    - Continuous Intergration server
        - Continuous Development, Build, Test, Deploy
- 다양한 플러그인 연동
    - Build Plugins : Maven, Ant, Gradle..
    - VCS Plugins : Git, SVN...
    - Languages Plugins : Java, Python, Node.js ...

Jenkins를 이용해서 형상관리를 하고 있는 대표적으로 Git에서 코드를 가져와서 빌드를 합니다.   
단위테스트를 하고 배포작업을 위해 패키징 작업을 할 수 있습니다.  
대표적으로 CI/CD tool 들에는 Jenkins, CircleCI, TeamCity, Bamboo, GitLab 등이 있습니다.  
오픈 소스로 되어 있는것은 젠킨스가 유일하고 나머지제품들을 무료로도 사용가능하지만 일정 서비스를 사용하려면  유료로 사용해야 합니다.   

# JenKins 설치

https://www.jenkins.io/download/  
다음 사이트에 들어가면 여러 OS에 따른 설치방법이 있습니다.  
저는 도커를 사용하여 설치하도록 하겠습니다.

https://hub.docker.com/r/jenkins/jenkins

위의 도커 허브 사이트에 접속해줍니다.

```
docker pull jenkins/jenkins
```

https://github.com/jenkinsci/docker/blob/master/README.md  

# Jenkins 실행 명령어

```
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins-server --restart=on-failure jenkins/jenkins:lts-jdk11
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins-server --restart=on-failure -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts-jdk11
```
-p 옵션은 컨테이너 내부에 있는 포트를 컨테이너밖에서 어떻게 접속할것인지하는 설정입니다.  
앞에 8080은 컨테이너 외부에서 8080 포트로 접속하면 컨테이너 내부에서 뒤에 있는 8080포트로 접속한다는 의미 입니다.

-v jenkins_home:/var/jenkins_home   
-v 옵션은 볼륨으로 현재 로컬 내 피씨하고 도커 컨테이너안에 디렉토리를 연결시켜주는 역할(마운트작업)을 합니다.

도커 이미지로 만든 젠킨스서버에 접속하는 방법은 http://localhost:8080 으로 접속해주면 됩니다.

그러면 비밀번호 입력창이 나오는데 docker logs jenkins-server 를 통해 로그를 확인한 뒤 비밀번호를 입력해줘야 접속이 가능합니다.

새로운 Item -> 프로젝트 이름을 작성해줍니다.  
그리고 Freestyle project 선택 후 OK를 눌러줍니다.  

Build Steps -> Add build step   

지금 만든 이 첫번째 아이템에서 이 아이템을 실행하게 되면 어떤 쉘 스크립트가 실행되도록 설정할 수 있습니다.  
지금 만들고 있는 Jenkins자체가 도커에 설치가 되었습니다. 도커는 기본적으로 운영체제가 리눅스입니다.  
따라서 쉘 스크립트가 실행되는 환경은 리눅스입니다. 

```
ehco "Welcome to my first project using Jenkins"
javac -version
```
저장을 해줍니다.

지금 빌드를 해주고 Console output를 확인해보면 자바버전까지 나오는걸 확인할 수 있습니다.

docker ps 를 통해 컨테이너를 출력해준뒤 접속해줍니다.

```
docker exec -it jenkins-server bash
cd /var/jenkins_home/workspace/My-First-Project
```
위의 경로에서 빌드시 패키징, 빌드한 결과물을 확인할 수 있습니다.