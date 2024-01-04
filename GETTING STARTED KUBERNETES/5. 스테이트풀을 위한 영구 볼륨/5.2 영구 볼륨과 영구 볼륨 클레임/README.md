# 영구 볼륨과 영구 볼륨 클레임

# 5.2.1 영구 볼륨의 필요성
기본적으로 디플로이먼트로 생성되는 파드는 휘방성이라는 특성이 있다.  
기본적으로 데이터는 사라지면 안되기 때문에 외부에 있는 저장소를 사용해야한다.


# 5.2.2 영구 볼륨과 영구 볼륨 클레임이란?
영구 볼륨(Persistent Volume)은 컨테이너, 파드, 노드의 종료와 무관하게 데이털르 영구적으로 보관할 수 있는 볼륨이다.  
쿠버네티스에서 영구 볼륨은 시스템 관리자가 외부 저장소에서 볼륨을 생성한 후 쿠버네티스에 연결하는 것을 의미한다.  
개발자가 디플로이먼트로 파들르 생성할 때 볼륨을 정의하는데 이때 볼륨 자체를 정의하느 것이 아니라 볼륨을 요청하는 볼륨 클레임을 지정한다.  
이때 영구 볼륨을 요청하는 볼륨 클레임을 영구 볼륨 클레임이라고 한다.
파드 -> 볼륨(클레임 네임) -> 영구 볼륨 클레임 -> 영구 볼륨 -> 외부 스토리지

# 5.2.3 영구 볼륨과 영구 볼륨 클레임 사용하기

## 영구 볼륨 생성
```bash
vi pv.yaml
```
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: mysql-pv-volume # 영구 볼륨 이름
  labels:
    type: local
spec:
  storageClassName: manual # 영구 볼륨 클레임의 요청을 해당 영구 볼륨에 바인딩
  capacity:
    storage: 20Gi # 영구 볼륨의 크기
    accessModes:
    - ReadWriteOnce 
    hostPath: # 호스트 경로를 사용하는 영구 볼륨
      path: "/mnt/data" # 외부 저장소가 준비되지 않아서 노드의 /mnt/dat 디렉터리를 볼륨으로 사용
```

```bash
kubectl apply -f pv.yaml
```

## 영구 볼륨 클레임 생성
```bash
vi pvc.yaml
```
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-pv-claim # 영구 볼륨 클레임 이름
spec:
  storageClassName: manual # 영구 볼륨 클레임의 요청을 해당 영구 볼륨에 바인딩
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
    storage: 20Gi # 영구 볼륨의 크기
```
    
```bash
kubectl apply -f pvc.yaml
```

## 파드 생성
mysql 용도의 디플로이먼트(파드)를 생성한다.
```bash
vi pvc-deployment.yaml
```
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
sepc:
  selecor:
    matchLabels:
      app: mysql
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - image: mysql:8.0.29
      name: mysql
      env:
      - name: MYSQL_ROOT_PASSWORD
        value: "password"               # mysql root 계정의 비밀번호
      ports:
      - containerPort: 3306             # mysql에서 사용하는 포트
        name: mysql
      volumeMounts:
      - name: mysql-persistent-storage
      mountPath: /var/lib/mysql          # /var/lib/mysql 디렉터리에 영구 볼륨을 마운트
    volumes:
    - name: mysql-persistent-storage
      persistentVolumeClaim:             # 영구 볼륨 할당 요청
      claimName: mysql-pv-claim
```
```bash
kubectl apply -f pvc-deployment.yaml
```

## 서비스 생성
```bash
vi mysql_service.yaml
```
```yaml
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  clusterIP: None
  ports:
  - port: 3306
  selector:
    app: mysql  
```

```bash
kubectl create -f mysql_service.yaml
```

## 상태 확인
### 디플로이먼트 상태 확인
```bash
kubectl describe deployment mysql
```
여기서 문제가 있으면 error라는 메시지가 보인다.  
Mounts에서 /var/lib/mysql 디렉터리에 영구 볼륨이 마운트되었는지 확인한다.

### 영구 볼륨 상태 확인
```bash
kubectl get pv
```
- RECLAIM POLICY : 영구 볼륨이 삭제되었을 때 영구 볼륨의 데이터를 어떻게 처리할지를 나타낸다.
  - Retain 정책: 영구 볼륨 클레임이 삭제되어도 저장소에 저장되어 있던 파일을 삭제하지 않는 정책이다.
  - Delete 정책: 영구 볼륨 클레임이 삭제되면 영구 볼륨과 연결된 저장소 자체를 삭제한다.
  - Recycle 정책: 영구 볼륨 클레임이 삭제되면 영구 볼륨과 연결된 저장소 데이터는 삭제한다. 하지만 저장소 볼륨 자체를 삭제하지 않는다.

- Status
  - Available(사용 가능): 아직 클레임에 바인딩 되지 않는 상태이다.
  - Bound(바인딩): 볼륨이 클레임의 의해 바인딩된 상태이다.
  - Released(릴리즈): 클레임이 삭제되었지만 클러스터에서 아직 리소스가 반환되지 않은 상태이다.
  - Failed(실패): 볼륨이 자동 반환에 실패한 상태이다.

### 볼륨에 대한 자세한 정보 확인
```bash
kubectl describe pv mysql-pv-volume
```
- Name: 영구 볼륨의 이름
- Labels: 영구 볼륨에 설정된 라벨
- Capacity: 영구 볼륨의 용량

### 영구 볼륨 클레임 상태확인
```bash
kubectl get pvc
```
- ACCESS MODES : 볼륨에 접근할 수 있는 모드를 나타낸다.
  - RWO(ReadWriteOnce): 하나의 노드에서 해당 볼륨이 읽기-쓰기로 마운트된다.
  - ROX(ReadOnlyMany): 볼륨은 많은 노드에서 읽기 전용으로 마운트된다.
  - RWX(ReadWriteMany): 볼륨은 많은 노드에서 읽기-쓰기로 마운트된다.
  - RWOP(ReadWriteOncePod): 볼륨이 단일 파드에서 읽기-쓰기로 마운트된다. 즉, 전체 클러스터에서 단 하나의 파드만 해당 영구 볼륨 클레임을 읽거나 쓸 수 있어야 하는경우 사용한다.

```bash
kubectl describe pvc mysql-pv-claim
```
Status- Bound(바인딩) 상태등, Used By(사용중인) 파드의 정보를 확인할 수 있다.
