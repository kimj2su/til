# 디플로이먼트와 서비스 사용하기
디플로이먼트는 쿠버네티스에서 상태가 없는(stateless) 애플리케이션을 배포할때 사용한다.  
그뿐만 아니라 레플리카셋의 상위 개념으로 파드의 개수를 유지할 뿐만아니라 배포 작업을 세분화해 관리할 수 있게 해준다.


# 디플로이먼트 배포 전략
디플로이먼트 배포 전략은 주로 애플리케이션이 변경될 때 사용한다.  
이전 버전의 애플리케이션에서 업데이트가 필요한 경우에 주로 사용되며, 배포 방법으로는 롤링, 재생성, 블루/그린, 카나리가 있다.

## 롤링 업데이트
쿠버네티스에서 사용하는 표준 배포 방식이다.  
롤링(rolling) 업데이트는 새 버전의 애플리케이션을 배포할 때 새 버전의 애플리케이션은 하나씩 늘려가고 기존 버전의 애플리케이션은 하나씩 줄여나가는 방식이다.  
업데이트가 느린 단점이 있음  

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: rolling
spec:
  replicas: 3
  template:
        metadata:
        labels:
          app: rolling
        spec:
          containers:
          - name: rolling
            image: nginx:1.16.1
            ports:
            - containerPort: 80
```
위에서 롤링 업데이트 부분을 추가하면 다음과 같다.
```yaml
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
```
- maxSurge: 최대로 늘어날 수 있는 파드의 개수
- maxUnavailable: 업데이트 중에 사용할 수 없는 파드의 개수 0보다 큰 정수를 지정할 수 있고 실습과 같이 퍼센트로 지정할 수 있다.


## 재생성 업데이트
재생성 업데이트는 새 버전의 애플리케이션을 배포할 때 기존 버전의 애플리케이션을 모두 삭제하고 새 버전의 애플리케이션을 생성하는 방식이다.  
새로운 버전의 파드에 문제가 발생하면 대처가 늦어질 수 있다는 단점이 있다.
```yaml
spec:
  replicas: 3
  strategy:
    type: Recreate
```

## 블루 / 그린 업데이트
이전 버전(블루, V1 파드)과 새 버전(그린, V2 파드)이 동시에 운영 된다.  
새로운 버전의 파드에 문제가 발생했을때 빠르게 대응가능 하지만 많은 파드가 필요하므로 많은 자원을 필요로하는 단점이 있다.

```yaml
apiVersion: apps/v1
kind: Service
metadata:
  name: bluegreen
spec:
  selector:
    app: bluegreen
  version: v1.0.0 # 트래픽이 신규 버전의 애플리케이션으로 변경되는 시점에 변경
```
버전으로 구분해 관리한다.

## 카나리 업데이트
카나리는 주로 애플리케이션의 몇몇 새로운 기능을 테스트할 때 사용한다.  
두 개의 버전을 모두 배포하지만 새 버전에는 조금씩 트래픽을 증가시켜 새로운 기능을 테스트 한다.  
기능 테스트에 문제가 없다고 하면 새 버전의 파드를 모두 늘려서 트래픽을 전환한다.


| 피드 |                                           디플로이먼트                                            |
|:---:|:-------------------------------------------------------------------------------------------:|
| 단일 컨테이너 또는 관련 컨테이너들의 그룹 | - 쿠버네티스에서 사용되는 용어로 파드의 원하는 동작이나 특성을 정의하는 파일<br/> -각 파드의 상태를 모니터링 <br/>-개발과 운영 환경에서 사용하기에 적합 |


# 디플로이먼트와 서비스 사용하기
디플로이먼트는 이름 그대로 배포에 관한 객체이다.  
단순히 파드를 배포하는것뿐만 아니라 몇 개의 파드를 실행할지 결정하는 것도 디플로이먼트다.  

기본 적으로 파드는 같은 노드에 떠 있는 파드끼리만 통신 가능  
외부와의 통신을 위해서는 CNI 플러그인이 필요함  
하지만 CNI 플러그인만으로는 통신 불가능
외부에서 파드에 접속하려면 서비스를 이용해야한다.

```yaml
vi nginx-deploy.yaml
apiVersion: apps/v1
kind: Deployment
metadata:                   # 디플로이먼트 정보
  name: nginx-deploy        # nginx-deploy라는 이름의 디플로이먼트 생성
  labels:
    app: nginx              # 디플로이먼트의 레이블
spec:
  replicas: 2               # 2개의 파드 생성
  selector:                 # 디플로이먼트가 관리할 파드를 선택
    matchLabels:
      app: nginx            # 디플로이먼트는 nginx 레이블을 갖는 파드를 선택해 관리
  template:                 # 파드 템플릿에 정의된 내용에 따라 파드를 생성
    metadata:
      labels:
        app: nginx          # 파드의 레이블
    spec:                   # 컨테이너에 대한 정보
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
```

spec.template.metadata.label의 설정과 spec.selector.matchLabels의 설정이 같아야한다.

```bash
kubectl apply -f nginx-deploy.yaml
```

### 레이블이란?
레이블이란 오브젝트에 키-값 쌍으로 리소스를 식별하고 속성을 지정하는데 사용한다.  
일종의 카테고리라 이해하면 된다.
사용 방법

- release: v1 / v2
- enviroment: dev / production
- tier : frontend / backend
- app: webapp / middleware

### 디플로이먼트, 레플리카셋, 파드의 이름이 붙는 형태
1. 디플로이먼트 - deploy
2. 레플리카셋 - deploy-xxxxx(임의로 생성)
3. 파드 - deploy-xxxxx-xxxxx(임의로 생성)

# 서비스 생성
```bash
vi nginx-svc.yaml

apiVersion: v1
kind: Service
metadata:
  name: nginx-svc  # 서비스 이름
  labels:
    app: nginx     # 서비스의 레이블
spec:
  type: NodePort : 노드포트를 이용해서 서비스를 외부에 노출시킴
  ports:
  - port: 8080
    nodePort: 31472
    targetPort: 80
  selector:
    app: nginx     # 서비스를 nginx 레이블을 갖는 파드에 연결
    
    
kubectl apply -f nginx-svc.yaml
kubectl get svc # 서비스의 상태 확인
```
