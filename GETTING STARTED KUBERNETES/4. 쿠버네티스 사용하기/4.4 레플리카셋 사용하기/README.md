# 레플리카셋 사용하기
레플리카셋은 일정한 개수의 동일한 파드가 항상 실행되도록 관리한다.  
이러한 기능이 필요한 이유는 서비스의 지속성 때문이다. 노드의 하드웨어에서 발생하는 장애 등의 이유로 파드를 사용할 수 없을 때 다른 노드에서 파드를 다시 생성해서 사용자에게 중단 없는 서비스를 제공할 수 있다.  


# 레플리카셋의 매니페스트
```bash
vi replicaset.yml
```

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: 3-replicaset # 레플리카 이름
spec:
  template:
    metadata:
      name: 3-replicaset
      labels:
        app: 3-replicaset
    spec:
      containers:
      - name: 3-replicaset
        image: nginx
        ports:
        - containerPort: 80
  replicas: 3 # 파드 개수
  selector:
    matchLabels:
    app: 3-replicaset # 레이블 선택
```

## 레플리카셋 생성
```bash
kubectl apply -f replicaset.yml
```

## 레플리카셋의 파드의 상태 확인
여러개를 같이 확인하려면 , 로 구분한다.
```bash
kubectl get replicaset, pods
```

## 레플리카셋의 파드 삭제
```bash
kubectl delete pod 3-replicaset-xxxxx
```
이때 삭제를 했지만 3개의 파드가 떠 있다.  
위에서 설명한대로 레플리카셋은 파드의 개수를 일정하게 유지한다.

## 레플리카셋의 파드 개수 늘리고 줄이기
```bash
kubectl scale replicaset/3-replicaset --replicas=5  
# --replicas=5 는 파드의 개수를 5개로 늘린다는 의미
```

## 파드는 유지하고 레플리카셋만 삭제하기
```bash
kubectl delete -f replicaset.yml --cascade=orphan
```
