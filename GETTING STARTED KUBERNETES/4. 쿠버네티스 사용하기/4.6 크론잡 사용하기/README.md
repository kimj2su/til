# 크론잡 사용하기
쿠버네티스에서 사용하는 잡은 다음과 같은 두가지 유형이 있다.
1. 잡(job) 혹은 Run to Completion 이라고도 불림
2. 크론잡(cronjob) 혹은 Scheduled Job 이라고도 불림

첫번째 잡은 특정한 파드의 정상적인 상태를 유지할 수 있도록 관리한다.  
즉 노드에 문제가 발생해서 특정 파드에 문제가 발생하면 정상적인 서비스를 유지하도록 새로운 파드를 다시 만드는 역할을 한다.

두번째 크론잡은 특정 시간에 특정한 작업을 수행하도록 한다.  
어떤 액션을 얼마나 자주 발생시킬지는 매니페스트에서 지정한다.  
주로 애플리케이션이나 데이터를 백업해야할 때 주로 사용한다.

## 크론잡 생성
```bash
vi cronjob.yaml
```
```yaml
apiVersion: batch/v1
kind: CronJob # 크론잡 배포
metadata:
  name: hello
spec:
  schedule: "*/1 * * * *" # 매분마다 실행
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: busybox # busybox 이미지 사용 -> ㅋ쿠버 공홈에도 게시되어 있는 최소한의 리눅스
            imagePullPolicy: IfNotPresent # 데스크톱 / 서버에 이미지가 없을때에만 내려받음
            command: # 스케줄에 따라 다음의 명령을 실행
            - /bin/sh
            - -c
            - date; echo Hello from the Kubernetes cluster
            restartPolicy: OnFailure # 잡이 실패했을 때만 재시작
```

```bash
kubectl create -f cronjob.yaml
```

## 스케줄의 의미
```java
* * * * * *
```
순서대로 
- 분(0-59)
- 시(0-23)
- 일(1-31)
- 월(1-12)
- 요일(0-6) 일요일 0 부터 토요일 6 까지

### 크론잡 상태(목록) 확인
```bash
kubectl get cronjob hello
kubectl get cronjob hello -w
kubectl get pod -w
```

### 크론잡 삭제
```bash
kubectl delete cronjob hello
```










































