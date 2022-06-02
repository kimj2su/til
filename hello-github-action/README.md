# 저장소 설명
Github Action을 이용한 CI/CD 샘플 프로젝트


### 필수 의존성
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

# 1. Github Action 이란?


- ## CI(Continuous Integration) & CD(Continuous Delivery) 플랫폼 
- ## 깃 이벤트 기반의 명령을 실행해주는 기능
- ## 빌드, 배포, 테스트 자동화
- ## 워크 플로우 생성

<br/><br/>

# 2. Github Action 구성
- ### 워크플로(Workflows) 
- ### 이벤트(Events) 
- ### 잡(Jobs)
- ### 스텝(Steps) 
- ### 액션(Actions) 
- ### 러너(Runner)

<br/><br/>

## 워크플로(Workflows)
- ### 저장소에 포함되는 자동화된 처리. 
- ### 자동화 된 처리를 위한 하나 또는 그 이상의 잡을 설정 
- ### 자신의 프로젝트 저장소에 YAML file 생성
- ### 실행 방법
    - 수동으로 실행
    - 저장소 이벤트에 따라 자동 실행 
    - 스케쥴에 따라 실행
- ### 워크 플로 정의: .github/workflows
- ### 여러 개의 워크 플로 정의 (dev workflow, stage workflow, prod workflow)

## Events
- ### 워크플로를 실행하는데 사용됨 
- ### 이벤트 예시
    - Pull-request 생성
    - 이슈 오픈
    - 커밋 Push 스케쥴
    - REST API call 수동

## Jobs
- ### 워크플로의 기본 작업 단위
- ### 같은 실행 Runner의 워크플로에서 동작하는 스텝의 모음 
- ### Job은 기본적으로 의존성이 없음, 각각이 병렬 
- ### 실행 Job과 Job의 의존성을 설정 가능
- ### Job이 다른 Job에 의존성이 있는 경우 해당 Job을 기다림

## Steps
- ### Job 안에서 실행되는 액션들의 모음
- ### 각 스텝은 쉘 스크립트나 액션을 동작
- ### 스텝은 순서에 따라 진행
- ### 같은 Runner에서의 스텝끼리는 데이터 공유

## Actions
- ### 깃허브 액션 플래폼에 복잡하거나 자주 반복되는 작업을 실행하는 사용자 지정 어플 리케이션
- ### 워크플로 파일에 작성하는 반복적인 코드 작성 줄여줌
- ### 가장 작은 단일 명령
- ### 직접만들수있다. 
- ### 만들어진것을가져와쓸수있다.

## Runners
- ### 이벤트 트리거에 의해 워크플로를 실행하기 위한 서버 
- ### 각 실행될 때 하나의 잡을 실행 
- ### 워크플로실행러너OS환경제공
    - Ubuntu Linux 
    - Microsoft Windows 
    - macOS
- ### 매실행시새가상환경제공
- ### 다른OS나다른하드웨어사양은직접호스팅가능

# 3. Github Action 만들기
- ## 깃허브 원격 저장소에 만들기
- ## 깃 로컬 저장소에 만들기

## 깃허브 원격 저장소에 만들기
- ### 저장소에서 "Actions" 탭 선택
- ### 깃허브 액션 생성 - "New workflow"
## 깃 로컬 저장소에 만들기
- ## 프로젝트 > .github > workflows> [워크플로].yml 

## Github Action 이벤트

- ### 워크플로를 실행하게 하는 이벤트 
- ### 이벤트를 사용하여 워크플로 트리거 - on 
- ### 단일 이벤트
    - on: push
- ### 여러 이벤트
    - on: [push,fork]
- ### 자주 사용하는 이벤트        
    - pull_request, push, schedule, workflow_dispatch

## Github Action 표현식
- ## 워크플로 파일 안에서 식 사용 가능
- ## 환경 변수 활용
- ## 프로그래밍 방식으로 설정
- ## 컨텍스트 접근
- ## 연산자 사용 가능   
- ## ${{<표현식>}}

```
name: hello-github-actions
run-name: ${{ github.actor }}의 헬로 깃허브

on: [push]

jobs: (잡 목록)
  hello-spring-boot: (잡을 정의 한다.)
    runs-on: ubuntu-latest    (잡을 실행하는 서버 플랫폼)
    steps: (잡에 포함되는 스텝들))
      - uses: actions/checkout@v3 (미리 정의된 액션 - 재사용 가능)
      - uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      - run: chmod +x gradlew   (각각의 개별 스텝 - 스크립트 또는 액션)
      - run: ./gradlew clean build test
```


# 4. 깃허브 액션 템플릿
- ## 빌드, 테스트 관련 템플릿
- ## 배포 관련 템플릿
- ## 자동화 관련 템플릿
- ## CI 관련 템플릿
- ## 정적 페이지 배포 템플릿

## Actions 탭에 Suggested for this repository 에서 템플릿을 고를 수 있다. 아니면 템플릿 마켓 플레이스가 있다.

# 5. 깃허브 액션 스케쥴
- ## 시간 예약하고 예약된 시간에 이벤트가 발생
- ## POSIX cron 문법을 따른다.


## * * * * * 
- ### 순서대로 minute(0~59)
- ### hour(0 ~ 23)
- ### day of month (1~ 31)
- ### month (1 ~ 12 or JAN-DEC)
- ### day of week (0 ~ 6 or SUN-SAT) 이다.

Cron문법연습방법  
https://crontab.guru/  
https://crontab.guru/examples.html

# 6. 컨텍스트

- ## 각상황에서접근가능한텍스트또는객체를얻을수있다.  
- ## 워크플로, 실행 환경, 작업, 스텝, 민감 정보, 입력

## 컨텍스트값사용문법
# ${{< context >}}

## secrets 컨텍스트


- ## 깃허브 프로젝트 >> settings >> security >> secrets & variables
- ##  민감정보깃허브시크릿설정
- ## 비밀번호
- ## API 토큰
- ## 인증서


## secrets 컨텍스트 등록
깃허브 프로젝트 >> settings >> security >> secrets & variables

## secrets 컨텍스트

### 민감정보깃허브액션사용
${{ secrets.GITHUB_TOKEN }}


## inputs 포함 워크플로  
입력값을 활용한 워크플로 실행