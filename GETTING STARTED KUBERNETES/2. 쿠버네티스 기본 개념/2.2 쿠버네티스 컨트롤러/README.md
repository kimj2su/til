# 쿠버네티스 컨트롤러
쿠버네티스 컨트롤러는 파드를 관리한느 역할을 한다.
쿠버네티스에서 제공하는 컨트롤러는 데몬셋, 디플로이먼트, 레플리카셋, 스테이트풀셋, 잡 ,크론잡, 레플리케이션 컨트롤러가 있다.  
용도에 따라 선택적으로 컨트롤러를 사용하는데 예를 들어 데몬셋은 클러스터 전체(모든 워커 노드)에 파드를 배포할 때 사용한다.  
각각의 사용법은 4장에서 다룬다.

# 2.2.1 디플로이먼트
디플로이먼트(Deployment)는 쿠버네티스에서 상태가 없는 애플리케이션을 배포할 때 사용하는 가장 기본적인 컨트롤러이다.  
레플리카셋의 상위 개념이면서 파드를 배포할 때 사용한다. 또한 파드를 배포할 때 다양한 방법(4.3 디플로이먼트와 서비스 사용하기 참조)를 지원해서 배포할 때 세밀한 조작이 가능하다.  
디플로이먼트는 다음과 같은 형식의 매니페스트를 사용한다.
```yaml
apiVersion: apps/v1   # 쿠버네티스의 apps/v1 API를 사용
kind: Deployment      # 디플로이먼트 작업으로 명시
metadata:
  name: deploy-test   # 디플로이먼트 이름 설정
  labels:
    app: deploy-test  # 디플로이먼트에 레이블 설정
spec:
  replicas: 3         # 파드 개수 설정
  selector:           # 레플리카셋과 동일하게 레이블 셀렉터 설정, 어떤 레이블의 파드를 선택해 관리할지 설정하는 부분, 여기서는 deploy-test라는 레이블의 파드를 관리하겠다는 의미
    matchLabels:
      app: deploy-test
  template:           # 파드 템플릿 설정
    metadata:         # 어떤 파드를 실행할지 설정하는 부분, 여기서는 deploy-test라는 레이블의 파드를 실행하겠다는 의미
      labels:
        app: deploy-test
    spec:             # 컨테이너에 대한 설정(컨테이너 이름, 이미지, 포트 정보 등을 설정)
      containers:
      - name: deploy-test
        image: nginx:latest # nginx 이미지 사용
        ports:
        - containerPort: 80 # 컨테이너 포트 설정
```

# 2.2.2 레플리카셋
레플리카셋(ReplicaSet)은 몇개의 파드를 유지할지 결정하는 컨트롤러이다.  
예를 들어 5개의 파드를 유지하도록 설정했으면 1개의 파드를 삭제해도 5개의 파드를 유지하기 위해 다른 파드를 1개를 생성한다.
```yaml
apiVersion: apps/v1         # 쿠버네티스의 apps/v1 API를 사용
kind: ReplicaSet            # ReplicaSet 작업으로 명시
metadata:
  name: replica-test        # 디플로이먼트 이름 설정
spec:
  template:                 # 파드 템플릿 설정
    metadata:               # 어떤 파드를 실행할지 설정하는 정보
      name: replica-test    # 생성될 파드의 이름
      labels:
        app: replica-test
    spec:                   # 컨테이너에 대한 설정(컨테이너 이름, 이미지, 포트 정보 등을 설정)
      containers:
      - name: replica-test
        image: nginx
        ports:
        - containerPort: 80 # 컨테이너 포트 설정
  replicas: 3 # 파드 개수 설정
  selector: # 레플리카셋과 동일하게 레이블 셀렉터 설정, 어떤 레이블의 파드를 선택해 관리할지 설정하는 부분, 여기서는 replica-test라는 레이블의 파드를 관리하겠다는 의미
    matchLabels:
      app: replica-test
```

참고로 레플리카셋과 유사한 기능을 가진 레플리케이션 컨트롤러(ReplicationController)도 있다.  
특정 파드의 개수를 유지하는 기능은 같지만 다음과 같은 차이점이 있다.(최근에는 레플리케이션 컨트롤러는 잘 사용하지 않는 추세이다.)

| 구분            | 레플리카셋                                  | 레플리케이션 컨트롤러                                |
|---------------|----------------------------------------|--------------------------------------------|
| 셀렉터(selector) | 집합 기반으로 in, not in, exists 같은 연산자를 지원함 | 등호 기반으로 레이블을 선택할 때 =, !=와 같이 같은지, 다른지만 비교함 |
| 롤링 업데이트       | 롤링 업데이트를 사용하려면 디플로이먼트를 이용해야함           | 롤링 업데이트 옵션을 지원함                            |

### 롤링 업데이트
기존에 배포된 애클리케이션(파드)을 수정으로 재배포할 때, 새 버전의 애플리케이션이 포함된 파드는 하나씩 늘리고 이전 버전의 애플리케이션이 포함된 파드는 줄여가는 방식이다. -> 4장


# 2.2.3 잡
잡(Job)은 하나 이상의 파드를 지정하고 지정된 수의 파드가 성공적으로 실행되도록 한다.  
예를 들어 노드의 하드웨어 장애나 재부팅 등으로 파드가 비정상적으로 작동하면(혹은 실행되지 않으면) 다른 노드에서 파드를 시작해 서비스가 지속되도록 한다.  
```yaml
apiVersion: batch/v1 # 쿠버네티스의 batch/v1 API를 사용
kind: Job            # Job 작업으로 명시
metadata:
  name: job-test     # Job 이름 설정
spec:
  template:          # 파드 템플릿 설정
    metadata:        # 어떤 파드를 실행할지 설정하는 정보
      name: job-test # 생성될 파드의 이름
    spec:            # 컨테이너에 대한 설정(컨테이너 이름, 이미지, 포트 정보 등을 설정)
    containers:
    - name: job
      image: busybox # 컨테이너 생성에 사용할 이미지
      command: ["echo", "job-test"] # 컨테이너에서 실행할 명령어
    restartPolicy: Never # 파드의 재시작 정책을 설정, 파드가 비정상적으로 종료되면 재시작하지 않음
```

# 2.2.4 크론잡
크론잡(CronJob)은 잡의 일종으로 특정 시간에 특정 파드를 실행시키는 것과 같이 지정한 일정에 따라서 잡을 실행시킬 때 사용한다.  
따라서 크론잡은 주로 애플리케이션 프로그램, 데이터베이스와 같이 중요한 데이터를 백업하는데 사용한다.
```yaml
apiVersion: batch/v1beta1 # 쿠버네티스의 batch/v1beta1 API를 사용
kind: CronJob             # CronJob 작업으로 명시
metadata:
  name: cronjob-test      # CronJob 이름 설정
spec:
  schedule: "*/1 * * * *"  # 크론잡의 실행 주기를 설정, 매 분마다 실행
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: cronjob
            image: busybox # 컨테이너 생성에 사용할 이미지
            args:
            - /bin/sh
            - -c
            - date; echo Hello this is Cron test
          restartPolicy: Never
```

# 2.2.5 데몬셋
데몬셋(DaemonSet)은 디플로이먼트 처럼 파드를 생성하고 관리한다.  
디플로이먼트가 실행해야 할 파드의 개수와 배포 전략을 세분화에 조작할 수 있다면, 데몬셋은 특정 노드 또는 모든 노드에 파드를 배포하고 관리한다.  
따라서 데몬셋은 주로 노드마다 배치되어야하는 성능 수집 및 로그 같은 작업에 사용된다.
```yaml
apiVersion: apps/v1         # 쿠버네티스의 apps/v1 API를 사용
kind: DaemonSet             # DaemonSet 작업으로 명시
metadata:
  name: daemonset-test      # DaemonSet 이름 설정
  labels:
    app: daemonset-test     # 데몬셋을 식별할 수 있는 레이블을 지정
spec:
  selector
    matchLabels:
      app: daemonset-test # 데몬셋을 식별할 수 있는 레이블을 지정
    template:               # 파드 템플릿 설정
      metadata: 
        labels:
          app: daemonset-test 
      spec: # 컨테이너에 대한 설정(컨테이너 이름, 이미지, 포트 정보 등을 설정)
        tolerations:
          - key: node-role/master
            effect: NoSchedule 
        containers:
        - name: busybox
          image: nginx
          args:
          - sleep
          - "10000"
```

# 테인트와 톨러레이션
쿠버네티스 클러스터를 운영하다 보면 특정 워커 노드에는 특정 성격의 파드만 배고하고 싶을 때가 있다.
이럴때 사용하는 것이 테인트(taint)와 톨러레이션(toleration)이다.  
테인트가 설정된 노드에는 일반적으로 사용되는 파드는 배포될 수 없으나 톨러레이션을 적용하면 배포할 수 있다.

```yaml
kubectl taint nodes worker1 color=red:NoExecute
```
- worker1 -> NODE_NAME : 원하는 워커 노드를 지정
- color=red -> KEY=VALUE : 테인트를 설정할 때 사용할 키와 값을 지정
- NoExecute -> EFFECT : 테인트의 효과를 지정, 3가지 옵션이 있다.
1. NoSchedule: 톨러레이션이 완전히 일치하는 파드만 배포할 수 있다.
2. NoExecute: 기존에 이미 배포된 파드를 다른 노드오 옮기고 새로운 파드는 배포되지 못하도록 한다.
3. PreferNoSchedule: NoSchedule과 유사하지만, 지정된 노드에는 새로운 파드가 배포되지 않지만 리소스가 부족할 때는 배포할 수 있는 차이가 있다.

따라서 톨러레이션을 지정한다는 것은 테인트가 적용된 워커 노드에도 새로운 파드를 배포할 수 있다는 의미이다.
톨러레이션은 다음과 같은 형식으로 사용된다. 이때 key, value, effect는 테인트에서의 값과 같은 의미이다.
```yaml
tolerations:
- key: [KEY] 
  operator: Equal
  value: [VALUE]
  effect: [EFFECT]
```
