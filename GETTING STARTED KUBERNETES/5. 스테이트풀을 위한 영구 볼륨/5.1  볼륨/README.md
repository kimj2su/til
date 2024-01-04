기본적으로 볼륨은 데이터를 보관하는 저장소라 생각하면 되고, 데이터를 저장소에 저장해 두는 애플리케이션을 스테이트풀이라고한다.  

# 5.1 볼륨이란?
볼륨은 데이터를 저장하는 저장소이다.  
기본적으로 MySQL 데이터베이스가 설치되어 있는 파드가 재시작되면 데이터는 모두 사라진다.  
파드(컨테이너)가 종료되어도 데이터를 보존할 수 있는 방법이 볼륨이다.  
볼륨은 데이터가 저장된느 위치에 따라 세가지로 분류할 수 있다.
- 파드 내에 위치 : 파드(컨테이너) 내에 데이터를 저장한다면 파드가 종료될 때 데이터도 함께 삭제 된다.
- 워커 노드 내에 취이 : 워커 노드 내에 데이터가 저장된다면 파드가 종료되어도 데이터가 유지되지만, 노드가 종료되면 데이터도 삭제된다.
- 노드 외부에 위치 : 파드 혹은 노드의 종료와 무관하게 데이터는 항상 보조된다.

### 볼륨의 라이프 사이클
볼륨은 파드의 라이프 사이클과 동일하다. 하지만 볼륨의 위치에 따라 조금씩 다르다.  


# 5.1.2 볼륨 유형
볼륨은 파드의 구성 요소이므로 컨테이너와 마찬가지로 매니페스트로 정의해 사용한다.  
볼륨은 독립적인 쿠버네티스 리소스가 아니므로 자체적으로 생성되거나 삭제 될 수 없다.  
볼륨은 파드뿐만 아니라 파드 내의 모든 컨테이너에서 사용할 수 있으며 접근하려는 각 컨테이너에 마운트해야 한다.  
볼륨의 유형은 임시 볼륨, 로컬 볼륨, 외부 볼륨이 있습니다.
- 임시 볼륨 : 파드  내의 공간을 사용하는 볼륨으로 파드 삭제되는 즉시 데이터도 삭제됩니다.
- 로컬 볼륨 : 노드 내의 디스크를 저장소로 사용하는 것이므로 노드가 종료되는 즉시 데이터도 삭제 된다.
- 외부 볼륨 : 노드의 외부에 있는 외부 저장소를 이용하는 것이므로 파드나 노드와는 무관하게 데이터를 영구적으로 사용할 수 있다. -> 외부 저장소 있어야함

|로컬 볼륨|로컬 볼륨|                              외부 볼륨                               |
|:---:|:---:|:----------------------------------------------------------------:|
|emptyDir|hostPath| nfs<br/>cephFs<br/>glusterFs<br/>iSCSI<br/>AWS EBS<br/>azureDisk |

## emptyDir
emptyDir는 파드가 생성될 때 같이 생성되고 파드가 삭제될 때 같이 삭제되는 임시 볼륨이다.

```bash
vi emptydir.yaml
```
```yaml
apiVersion: v1
kind: Pod # 리소스의 종류를 명시
metadata:
  name: emptydata # 파드 이름
spec:
  containers:
  - name: nginx # nginx라는 이름의 컨테이너를 생성해 /data/shared 디렉터리에 마운트
    image: nginx
    volumeMounts:
    - name: shared-storage # 볼륨 이름
      mountPath: /data/shared # 볼륨을 마운트할 디렉터리 경로
  volumes:
  - name: shared-storage # 볼륨 이름
    emptyDir: {} # shared-storage라는 이름으로 emptyDir리반의 볼륨을 생성
```

yaml 파일을 통해 파드를 생성
```bash
kubectl apply -f emptydir.yaml
```

파드 접속 및 /data/shared 디렉터리 접속하여 파일 생성
```bash
kubectl exec -it emptydata -- /bin/bash
cd /data/shared
echo "Hello, World!" > test.txt
date;cat test.txt
```

## hostPath
hostPath는 노드의 로컬 디스크를 파드에 마운트해서 사용한느 로컬 볼륨이다.  
같은 노드를 사용하는 다수의 파드끼리 데이터를 공유해 사용할 수 있다.  
사용 중인 파드에 장애가 생겨 다른 노드에서 파드가 실행될 때는 hostPath 볼륨에 더 이상 접속할 수 없다.

```bash
vi hostpath.yaml
```
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: hostpath
spec:
  containers:
  - name: nginx
    image: nginx
    volumeMounts:
    - name: localpath
    mountPath: /data/shared # nginx라는 이ㄹ름의 컨테이너에 /data/shared 디렉터리에 마운트
    volumes:
    - name: localpath
      hostPath:
        path: /tmp # 워커 노드의 /tmp 디렉터리를 hostPath 볼륨으로 사용
        type: Directory
```

yaml 파일을 통해 파드를 생성 및 볼륨에 데이터 생성
```bash
kubectl apply -f hostpath.yaml
kubectl get pods
kubectl exec -it hostpath -- /bin/bash
cd /data/shared
echo "Hello, World!" > test.txt
```
