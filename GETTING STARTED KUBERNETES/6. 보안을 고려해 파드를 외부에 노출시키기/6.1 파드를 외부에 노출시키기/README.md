# 6.1 파드를 외부에 노출시키기
파드의 특징은 하나의 클러스터 내의 노드들로 옮겨 다닌다.  
이때 파드의 IP는 새로운 IP로 변경되기 때문에 외부에서(혹은 내부에서) 접근하기 어렵다.  
이러한 문제를 해결하기 위해 사용하는것이 쿠버네티스의 서비스이다.  
서비스를 사용하면 파드가 클러스터 내 어디에 있든지 마치 고정된 IP로 접근할 수 있는 것과 같다.  

서비스는 내부에서 접속하는것과 외부에서 접속하는 용도의 서비스에따라 다음과 같이 나뉩니다.

|클러스터 내부 접속 용도|                        클러스터 외부 접속용도                         |
|:---:|:-----------------------------------------------------------:|
|ClusterIP| ExternalName<br/>노드포트(NodePort)<br/>로드밸런서<br/>인그레스(Ingress) |

### 쿠버네티스에서 서비스란?
일반적으로 서비스란뜻은 웹서비스같은 서비스를 말하지만 쿠버네티스에서는 파드에서 실행중인 애플리케이션을 네트워크 서비스로 노출시키는 방법이라고 한다.

### 서비스를 생성하는 방법
1. 매니페스트를 이용해 생성하는 방법(yaml파일 이용)
2. kubectl expose 명령어를 사용하는 방법

# 6.1.1 ClusterIP
ClusterIP는 기본 서비스 타입으로, 클러스터 내 파드에 접근할 수 있는 가상의 IP이다.  
서비스는 기본적으로 ClusterIP를 갖는다.  
그리고 서비스를 파드에 연결해 놓으면 서비스IP(ClusterIP)를 통해서 파드에 접근할 수 있다.  
ClusterIP는 클러스터 내부에서만 접속할 수 있으며 외부에서는 접속할 수 없다.  

## ClusterIP 서비스 생성하기
```bash
vi ClusterIP.yaml
```
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: clusterip-nginx
spec:
  selector:
    matchLabels:
      run: clusterip-nginx
  replicas: 2
  template:
    metadata:
      labels:
        run: clusterip-nginx
    spec:
      containers:
      - name: clusterip-nginx
        image: nginx
        ports:
        - containerPort: 80
```
```bash
kubectl apply -f ClusterIP.yaml
```
매니페스트에서는 서비스 타입을 별도로 지정하지 않으면 ClusterIP가 기본값으로 설정된다.

```bash
kubectl get pods -l run=clusterip-nginx -o wide
```

- -l run=clusterip-nginx : 레이블이 run=clusterip-nginx인 파드만 조회
- -o wide : --ouutput=wide랑 동일어로 파드의 상세 정보를 확인할 때 사용한다.

## expose 명령어로 외부에 서비스 노출하기
```bash
kubectl expose deployment/clusterip-nginx
kubetcl get svc clusterip-nginx # 생성된 서비스의 상태 확인
kubectl describe svc clusterip-nginx # 생성된 서비스의 상세 정보 확인 여기서 IP 확인 가능
kubectl get ep clusterip-nginx # 파드의 엔드포인드 확인
```

### 엔드포인트란?
쿠버네티스에서 서비스와 파드의 통신은 서비스의 셀럭터(selector)와 파드의 레이블(label)을 이용한다.  
서비스의 셀렉터는 파드의 레이블을 가리키며, 서비스는 셀렉터에 매칭되는 파드를 찾아서 통신을 수행한다.  
만약 파드의 레이블이 서비스의 셀렉터에서 사용한 이름과 같으면 서비스는 해당 파드로 트래픽을 보낸다.  
이것이 가능한 이유는 서비스가 엔드포인트와 파드들을 매핑해 관리하기 때문이다.  
kubectl get pods -l run=clusterip-nginx -o wide 의 IP와 kubectl get ep clusterip-nginx의 IP가 같은것을 확인할 수 있다. 

# 파드에서 ClusterIP 서비스로 접근하기
```bash
kubectl run busybox --rm -it --image=busybox /bin/sh

wget 10.104.45.111

cat index.html
```
- kubectl run busybox: busybox라는 이름의 파드를 생성한다.
- --rm: 파드를 생성할 때 --rm 옵션을 주면 파드를 종료할 때 파드를 삭제한다.
- -it: 파드에 접속할 때 사용하는 옵션으로 -i는 파드와 표준 입력을 연결하고 -t는 터미널을 사용한다는 의미이다.
- --image=busybox: busybox 이미지를 사용한다.
- /bin/sh: 파드에 접속할 때 사용할 명령어를 지정한다.


## 오브젝트를 생성하는 방법
1. kubectl run 혹은 expose: 생성형(generative)
2. kubectl create: 명령형(Imperative)
3. kubectl apply: 선언형(declarative)

컨테이너가 원하는대로 작동하는지 빠르게 확인하려면 생성형을 사용한다.    
생성형은 오브젝트 생성 및 변경에 대한 히스토리를 저장하지 않아서 주로 개발환경에서 사용함.  
명령형과 선언형은 다음과 같은 차이가 있다.  
명령형은 최소 하나의 yaml이나 json파일을 이용해서 오브젝트를 생성해야하며 생성된 파일을 통해 히스토리를 추적할 수 있다.  
선언형은 애너테이션에 정보가 저장되어 자동으로 히스토리를 관리할 수 있다. 따라서 선언형에는 create, replace 명령을 지원하지 않는다.  

### 애너테이션
애너테이션이란 오브젝트(예, 디플로이먼트)에 메타데이터를 할당할 수 있는 주석과 같은 개념이다.  
레이블과 같이 키-값 구조를 갖지만, 기능에서 차이가 있다.  
레이블은 레이블 셀렉터를 이용해 검색과 식별을 할 수 있지만 애너테잇견에서는 입력은 가능해도 검색은 되지 않는다.  
하지만 쿠버네티스 클러스터의 API 서버가 애너테이션에 지정된 메타데이터를 참조해 동작한다는 점에서 주석 그 이상의 의미가 있다.  

애너테이션은 다음과 같은 메타데이터를 기록할 때 사용한다.
- 빌드, 릴리스, 도커이미지 등에 대한 정보(id, 이미지 해시, 레지스트리 주소)
- 로깅(logging), 모니터링 정보
- 디버깅에 필요한 정보(이름, 버전, 빌드 정보)
- 관리자 연락처
- 사용자 지시 사항

따라서 서비스 셀렉터와 같이 관련된 리소스 그룹을 지정해야 할떄는 레이블을 사용해야 한다.  
애너테이션에 기록된 것들은 쿠버네티스 외부(헬름(helm))등에서 사용해야 할 때는 애너테이션을 이용해야한다.

서비스를 만들때 Cluster IP를 지정하지 않으면(None으로 설정) Cluster IP가 없는 서비스가 만들어진다.  
이런 서비스를 헤드리스 서비스라고한다. 헤드리스 서비스는 주로 로드밸런싱이나 서비스IP가 필요 없을 때 사용한다.  
헤드리스 서비스에 셀렉터를 설정하면 API를 통해서 접근할 수 있는 엔드포인트가 만들어지지만 셀렉터가 없으면 엔드포인트가 만들어지지 않늗다.


# 6.1.2 ExternalName
ExternalName은 클러스터 내부에서 외부의 엔드포인트에 접속하기 위한 서비스이다.  
이때 엔드포인트는 클라스터 외부에 위치하는 데이터베이스나 애플리케이션 API등을 의미한다.

![image](https://github.com/jisu3316/til/assets/95600042/eeb794e1-efc9-4154-9df9-341789040152)

## ExternalName 서비스 생성하기
```yaml
apiVersion: v1
kind: Service
metadata:
  name: external-service
spec:
    type: ExternalName
    externalName: myservice.test.com # external-service와 연결하려는 외부 도메인 값을 설정
```
- 외부 FQDN(Fully Qualified Domain Name)을 externalName에 지정한다. 
- CNAME(Canonical Name record): external-service

- FQDN은 도메인에 대한 전체이름 표기 방식을 의미한다. 예를 들어 www.a.com 혹은 web01.a.com과 같은 형태가 FQDN이다.
- CNAME은 하나의 도메인 이름을 다른 이름으로 매핑시키는 역하를 하는 DNS 레코드의 일종이다.

즉 external-service를 호출하면 myservice.test.com으로 반환하는 것이 ExternalName 서비스이다.  
프록시나 특정 이름을 다른 이름으로 자동으로 변환하는 리다이렉트 용도로 사용한다.


# 6.1.3 노드포트
모든 워커 노드에 특정 포트를 열고 여기로 들어오는 모든 요청을 노드포트 서비스로 전달한다.  
그런 후에 노드포트 서비스는 해당 업무를 해당 업무를 처리할 수 있는 파드로 요청을 전달한다.  
즉, 노드포트 타입의 서비스는 외부 사용자(혹은 애플리케이션)가 클러스터 내부에 있는 파드에 접속할 때 사용하는 서비스중 하나이다.

![image](https://github.com/jisu3316/til/assets/95600042/6242f782-af79-412d-9542-3ee34063a230)

매니페스트를 이용해서 노드포트를 생성한다는 것은 노드포트 서비스를 하나 만들고 외부에서 어떤 포트로 접속할지를 지정하는 과정이다.  
또한 내부의 어떤 파드를 사용할지 셀렉터로 지정하고 포트를 정의한다.

```bash
vi nginx-deployment.yaml
```

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
    app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
```

### 디플로이먼트 생성
```bash
kubectl apply -f nginx-deployment.yaml
```

### 디플로이먼트 상태 확인
```bash
kubectl get deployments
```
### 파드생성
```bash
kubectl get pods -o wide
```

## 노드포트 파드 외부 노출시키기
```bash
vi nginx-svc.yaml
```
```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-nginx
  labels:
    run: my-nginx
spec:
  type: NodePort        # 외부에서 접속할 때 노드포트 서비스 사용
  ports:
    - port: 8080        # 노드포트 서비스에서 사용하는 포트
      targetPort: 80    # 파드(컨테이너)에서 사용하는 포트
      protocol: TCP
      name: http
  selector:
    app: nginx          # nginx 라는 이름의 레이블을 갖는 파드를 서비스
```

## 서비스 실행
```bash
kubectl apply -f nginx-svc.yaml
```
## 노드포트, 파드, 서비스의 관계

![image](https://github.com/jisu3316/til/assets/95600042/21e2ba5e-98e7-44ce-bede-512118362059)

노드 포트는 서비스를 통해 외부에 노출되기 때문에 서비스의 포트를 확인해야 한다.
```bash
kubectl get svc | grep my-nginx
```
노드포트를 이용해 서비스를 외부에 노출했기 때문에 노드(호스트)의 IP 뒤에 30472포트를 붙여서 접속할 수 있다.

## 노드포트의 제약사항
- 포트당 하나의 서비스만 사용할 수 있다.
- 30000에서 32767까지의 포트만 사용할 수 있다. 따라서 해당 범위를 넘는 서비스를 운영중이라면 사용이 불가능
- 노드나 가상 머신IP주소가 바뀌면 반드시 반영해야한다.

그래서 운영환경에서는 노드포트를 사용할때는 주의해야 한다.

# 6.1.4 로드밸런서
로드밸런서는 서버에 가해지는 부하(=로드)를 분산(=밸런싱)해주는 장치 또는 기술을 통칭한다. 

## L4 로드밸런서
L4 로드밸런서는 OSI 7계층에서 네트워크 계층(IP, IPX)이나 트랜스포트 계층(TCP, UDP)의 정보를 바탕으로 로드를 분산한다.  
따라서 IP주소나 포트 번호등을 이용해 트래픽을 분산하는 것이 L4 로드밸런서이다.

## L7 로드밸런서
애플리케이션 계층(HTTP, FTP, SMTP)을 기반으로 로드를 분산하기 때문에 URL, HTTP 헤더, 쿠키 등과 같은 사용자의 요청을 기준으로 트래픽을 분산한다.  
쿠버네티스에서 L7 로드밸런서의 기능은 인그레스를 사용해 구현할 수 있다.  

## 로드밸런스 사용해보기
보통 L4 로드밸런서는 외부에서 접근할 수 있는 IP를 이용해 로드밸런서를 구성한다. 즉, 외부 IP(공인 IP)를 로드밸런서로 설정하면 클러스터 외부에서 접근할 수 있다.  

### 외부 IP 사용 설정하기
```bash
kubectl create deployment httpd --image=httpd
cat << EOF > httpd-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: httpd-service
spec:
  selector:
    app: httpd
  ports:
    - name: httpd
      protocol: TCP
      port: 80
  externalIPs:
    - 172.30.1.44
EOF
```
cat << EOF는 입력한 텍스트를 파일에 저장할 때 사용한다.  
cat <<EOF > '파일명'으로 시작하고 마지막에 EOF로 끝낸다.

```bash
kubectl apply -f httpd-service.yaml
```
서비스를 확인하면 외부 IP 주소가 설정된 것을 확인할 수 있다.
```bash
mkdir service
cd service
vi load-balancer-example.yaml
```
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app.kubernetes.io/name: load-balancer-example
  name: hello-world
spec:
  replicas: 5 # 5개의 파드를 생성하도록 저장
  selector:
    matchLabels:
      app.kubernetes.io/name: load-balancer-example
  template:
    metadata:
      labels:
        app.kubernetes.io/name: load-balancer-example
    spec:
      containers:
      - image: gcr.io/google-samples/node-hello:1.0
        name: hello-world
        ports:
        - containerPort: 8080 # 컨테이너 포트를 8080으로 지정
```
다음과 같이 hello-world라은 애플리케이션을 디플로이형태로 배포하고 로드밸랜서를 위해 파드를 5개 생성한다.

```bash
kubectl apply -f load-balancer-example.yaml
```

```bash
kubectl get deployments hello-world
kubectl describe deployments hello-world # 디플로이먼트 상세 정보 확인
kubectl get replicasets # 레플리카셋 정보 확인
kubectl describe replicasets # 레플리카셋 상세 정보 확인
kubectl expose deployment hello-world --type=LoadBalancer --name=exservice # 외부 노출시키기 위한 서비스
kubectl get services exservice # 여기서 몇번 포트를 사용하는지 확인
kubectl describe services exservice # 서비스 상세 정보 확인
```
이때 마스터 노드와 워커 노드 어느쪽을 호출해도 응답이 된다.  
로드밸런서는 별도의 스위치 장비가 필요해서 비용이 많이 든다. 이때 대안으로 사용할수 있는것이 인그레스이다.  

# 6.1.5 인그레스
인그레스는 클러스터 외부에서 내부로 접근하는 요청을 어떻게 처리할지 정의해 둔 규칙들의 모음이다.    
따라서 인그레스는 일반적인 로드밸런서와는 다르게 IP가 아니라 URL로 경로를 찾아준다.  
즉, 인그레스는 어떤 URL 경로로 요청이 왔을때 어떤 서비스로 연결하라는 규칙을 정의한 것이 전부이다.  
인그레스 규칙에 맞게 경로를 찾는 행위를 하는것을 인그레스 컨트롤러이다.

[길벗 깃허브 링크](https://github.com/gilbutITbook/080329/tree/main/6%EC%9E%A5)
