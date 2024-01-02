# kind 설치하기

# 1. ubuntu 설정
## 1.1 패키지 최신 상태로 업데이트
```ubuntu
# root 계정으로 전환
sudo su -

# 리눅스를 최신 상태로 업데티으
sudo apt-get update && sudo apt-get upgrade 
```

## 1.2 패키지 관리자가 https를 통해 데이터와 패키지에 접근할 수 있도록 필요한 패키지 설치 
```ubuntu
sudo apt install apt-transport-https curl
```
- curl : URL로 데이터를 전송할 때 사용
- apt-transport-https : https를 통해 apt를 사용할 수 있도록 함

# 2. 도커 설치
## 2.1 도커 설치 및 도커 시작
```ubuntu
sudo apt install docker.io

sudo systemctl start docker
sudo systemctl enable docker # 재부팅 후에도 서비스가 자동으로 시작되도록 설정
sudo systemctl status docker # 도커 서비스 상태 확인
```

# 3. kind 설치
## 3.1 kind 설치
```ubuntu
sudo su
curl -Lo ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.10.0/kind-linux-amd64
chmod +x ./kind
mv ./kind /usr/local/bin/
```
- curl : URL로 데이터를 전송할 때 사용
- Lo ./kind
  - L : 서버에서 HTTP 301이나 HTTP 302 응답이 오면 지정된 URL로 이동한다.
  - o ./kind : ./kind라는 이름으로 파일을 저장한다.
- chmod +x ./kind : kind 파일에 실행 권한을 추가한다.
- mv ./kind /usr/local/bin/ : kind 파일을 /usr/local/bin/ 디렉터리로 이동한다.

## 3.2 kind 파일을 이용해 쿠버네티스 클러스터를 생성
```ubuntu
kind create cluster --name kubernetes
kubectl cluster-info # 클러스터 구성 정보 확인
kubectl config view # 쿠버네티스 클러스터 구성 정보 자세히 확인
```

# 4. 노드 확인
```ubuntu
kubectl get nodes
```

# 5. 클러스터 삭제
```ubuntu
kind delete cluster --name kubernetes
```

# 6. 멀티 노드 워커 노드 구성하기 - yaml
## 6.1 kind.yaml 파일 생성
```ubuntu
vi kind.yaml
```
```yaml
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:  # 마스터 노드 1대와 워커 노드 2대로 구성
- role: control-plane
- role: worker
- role: worker
```

## 6.2 kind.yaml 파일을 이용해 클러스터 생성
```ubuntu
kind create cluster --name kubernetes --config ./kind.yaml
kubectl get nodes # 노드 확인
```
