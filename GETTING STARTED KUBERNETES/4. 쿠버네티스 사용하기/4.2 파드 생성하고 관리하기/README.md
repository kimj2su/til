# 파드 생성하고 관리하기
파드는 쿠버네티스의 기본 배포 단위이며 다수의 컨테이너를 포함한다.

#### 사이드카 패턴
사이드카 패턴은 하나의 컨테이너가 다른 컨테이너를 돕는다는 의미이다.   
예를 들어, 웹 서버 컨테이너와 로그 수집기 컨테이너를 같이 실행하는 것이다.  
이렇게 하면 웹 서버 컨테이너는 로그 수집기 컨테이너를 실행할 필요 없이 로그를 수집할 수 있다.  
하나의 파드 안에 두개의 컨테이너가 포함되어 있는 형태이다.

## 1. 파드 생성하기
파드는 create 또는 apply 명령으로 생성할 수 있다
- kubectl create: 클러스터에 새로운 리소스를 생성
- kubectl apply: create와 replace(생성된 오브젝트를 삭제하고 새로운 오브젝트를 생성)의 혼합

즉 apply는 생성과 변경을 자유롭게 사용할 수 있다.

### create로 파드 생성하기
```bash
sudo su- # 사용자 변경
kubectl create deployment my-httpd --image=httpd --replicas=1 --port=80
kubectl get pods
```
- deployment: 스테이트리스 형태의 파드 생성
- my-httpd: 디플로이먼트 이름
- --image=httpd: 파드를 생성하는데 사용되는 이미지
- --replicas=1: running 상태를 유지할 파드 개수
- --port=80: 파드가 사용할 포트

### 디플로이먼트 생성 확인
```bash
kubectl get deployment

NAME       READY   UP-TO-DATE   AVAILABLE   AGE
my-httpd   1/1     1            1           93s
```
- READY: 레플리카의 개수
- UP-TO-DATE: 최신 버전의 레플리카 개수
- AVAILABLE: 사용 가능한 레플리카 개수
- AGE: 파드가 실행되고 있는 지속 시간

-o wide 옵션으로 추가적인 정보를 얻을 수 있다.
```bash
kubectl get deployment -o wide

NAME       READY   UP-TO-DATE   AVAILABLE   AGE     CONTAINERS   IMAGES   SELECTOR
my-httpd   0/1     1            0           2m33s   httpd        httpd    app=my-httpd
```

- CONTAINERS: 파드에 포함된 컨테이너
- IMAGES: 파드에 포함된 컨테이너의 이미지
- SELECTOR: yaml파일의 셀렉터를 의미(yaml파일의 셀렉터는 라벨이 app=my-httpd인 파드만을 선택해서 서비스를 하겠다는 의미)


### 디플로이먼트로 생성된 파드 확인
```bash
kubectl get pods

NAME                        READY   STATUS    RESTARTS        AGE
my-httpd-7547bdb59f-92qvq   1/1     Running   4 (2m39s ago)   4m59s
```
RESTARTS: 파드가 재시작된 횟수  
-o wide 옵션으로 추가적인 정보를 얻을 수 있다.
```bash
kubectl get pods -o wide
```
- IP : 파드에 할당된 IP
- NODE : 파드가 실행되고 있는 노드
- NOMAINATED NODE: 예약된 노드의 이름
- READINESS GATES: 파드상태 정보로 사용자가 수정할 수 있음. 예를 들어 running, pending 상태가 기본이지만 creating 같은 좀 더 상세한 정보를 원한다면 READINESS GATES를 다음과 같이 설정하면됨
```yaml
TYPE              STATUS
Initialized       True
ContainersReady   True
PodScheduled      True
```

# 파드 삭제하기

생성된 디플로이먼트와 파드를 삭제할때 둘다 delete를 사용한다.  
디플로이먼트를 삭제할때는 kubectl delete deployment [디플로이먼트 이름]을 사용하고 파드를 삭제할때는 kubectl delete pod [파드 이름]을 사용한다.  
하지만 디플로이먼트를 삭제하면 파드도 함께 삭제 된다.
```bash
kubectl delete deployment my-httpd
```

# 디플로이먼트, 파드 속성 변경
kubectl edit 명령어를 사용한다.
1. kubectl edit 명령어 실행
2. 필요한 부분 수정

```bash
kubectl create deployment my-httpd --image=httpd --replicas=1 --port=80
kubectl edit deployment my-httpd
```

# 파드 관리하기
파드에 접속할때는 exec 명령어를 사용한다.
```bash
kubectl get pods
kubectl exec -it [파드 이름] -- /bin/bash
```

- kubectl exec: 실행중인 파드에 접속
- -it: 표준 입력과 표준 출력을 사용
  - i: 컨테이너에 대화형 쉘을 생성
  - t: 터미널을 할당
- --/bin/bash: 쉘의 절대 경로

#로그 확인
```bash
kubectl logs [파드 이름]
```
