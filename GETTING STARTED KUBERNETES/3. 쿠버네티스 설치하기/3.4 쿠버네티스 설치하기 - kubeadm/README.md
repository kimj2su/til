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

# 2. 쿠버네티스 설치하기

1. 도커 설치
2. 스왑 메모리 비활성화하기
3. cgroup 드라이버 설정하기
4. 쿠버네티스 설치하기

## 2.1.1 도커 설치 및 도커 시작
```ubuntu
sudo apt install docker.io

sudo systemctl start docker
sudo systemctl enable docker # 재부팅 후에도 서비스가 자동으로 시작되도록 설정
sudo systemctl status docker # 도커 서비스 상태 확인
```

## 2.1.2 우분투 사용자를 도커 그룹에 추가
일반적으로 root 계정은 공유해서 사용하지 않는다. 따라서 일반 계정을 도커 그룹에 추가해야 한다.
```ubuntu
sudo usermod -aG docker $USER
```

## 2.2 스왑 메모리 비활성하기
kublet이 제대로 작동하려면 스왑 메모리를 비활성화해야한다.  
스왑 메모리란 물리 메모리가 부족할 경우를 대비해서 만들어 놓은 공간이다.
```ubuntu
sudo swapoff -a
sudo sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
free -h
```
두 번째 명령어는 시스템이 부팅될 때마다 스왑 메모리를 비활성화하도록 설정한다.  
free -h 명령어를 통해 스왑 메모리가 비활성화되었는지 확인할 수 있다.

## 2.3 cgroup 드라이버 설정하기
쿠버네티스에서는 도커의 cgroup 관리를 위해 systemd를 사용하도록 변경해야 한다.

### 2.3.1 cgroup 드라이버 확인하기
```ubuntu
docker info | grep Cgroup
```
위의 명령어를 통해 현재 cgroup 드라이버가 systemd로 설정되어 있는지 확인한다.

### 2.3.2 cgroup 드라이버 변경하기
만약 systemd가 아니라 cgroupfs로 설정되어 있다면 아래의 명령어를 통해 systemd로 변경한다.
```ubuntu
sudo vi /etc/docker/daemon.json
{
    "exec-opts": ["native.cgroupdriver=systemd"],
    "log-driver": "json-file",
    "log-opts": {
        "max-size": "100m"
    },
    "storage-driver": "overlay2"
}
```

### 2.3.3 도커 재시작
```ubuntu
sudo mkdir -p /etc/systemd/system/docker.service.d
sudo systemctl daemon-reload
sudo systemctl restart docker
```

## 2.4 쿠버네티스 설치하기 (kublet, kubeadm, kubectl, kubernetes-cni 설치)
쿠버네티스를 사용하려면 기본적으로 kublet, kubeadm, kubectl, kubernetes-cni가 필요하다.
- kublet : 클러스터의 각 노드에서 실행되는 에이전트로 파드 및 컨테이너의 생성/실행 등과 같은 작업 수행
- kubeadm : 쿠버네티스의 클러스터를 구축하기 위해 사용
- kubectl : 클러스터와 통신하는 명령어라인 인터페이스
- kubernetes-cni : 컨테이너가 데이터를 교환하고 통신할 수 있도록 컨테이너 내에서 네트워킹을 가능하게 함

### 2.4.1 kubeadm, kubelt, kubectl, kubernetes-cni 패키지가 위치한 레포지토리를 지정
```ubuntu
apt-get install gnupg
sudo apt-get install software-properties-common
sudo apt-get update

curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -

sudo apt-add-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main"
# kubeadm kubelt kubectl kubernetes-cni 설치
```

- s : silent의 약자로, 도커를 내려받는 과정이나 그 과정에서 오류가 발생하면 정보를 표시하지 않는다.
- apt-key : apt가 패키지를 인증할 때 사용하는 키를 관리하기 위해 사용한다. 이 키로 인증된 패키지는 신뢰할 수 있는것으로 간주 된다.
- add - : 키 목록에 새로운 키를 추가한다.
- apt-add-repository : apt 저장소를 추가한다.
- deb : 리눅스에서 설치 파일을 패키지라고 하는데 CentOS 등 레드햇 계열은 rmp 패키지 파일을 지원하고, 우분투 등 데비안 계열은 deb 패키지 파일을 지원한다.
  또한 패키지를 설치할때는 CentOS등 레드햇 계열에서는 yum을 사용하고, 우분투 등 데비안 계열에서는 apt-get을 사용한다.

### 2.4.2 kubeadm, kubelt, kubectl, kubernetes-cni 설치
```ubuntu
sudo apt install kubeadm kubelet kubectl kubernetes-cni
```

여기까지 마스터와 워커 노드에 공통으로 설치가 필요한 항목돌이였다.

# 3. 마스터 노드 설치하기
여기서는 마스터 노드에서만 설치를 진행한다.  
마스터 노드는 클러스터를 생성하는 것이 중요하다.  
클러스터는 마스터와 워커 노드를 하나의 관리 단위로 묶는 역할을 한다.

1. kubeadm으로 클러스터 생성
2. 클러스터 시작하기
3. 파드 네트워크 설정

## 3.1 kubeadm으로 클러스터 생성
```ubuntu
sudo kubeadm init --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=198.19.249.75
```
- kubeadm init : 쿠버네티스 클러스터 생성
- --pod-network-cidr : 파드 네트워크를 위한 CIDR을 지정한다. CIDR은 IP 주소의 범위를 나타내는 방법이다.
- --apiserver-advertise-address : 마스터 노드의 IP 주소를 지정한다. 수신 대기 중임을 알릴 IP 주소로 마스터 노드의 IP를 설정

### 3.1.1 오류 해결 방법
1. container runtime 오류
```ubuntu
[ERROR CRI] : container runtime is not running
-> rm /etc/containerd/config.toml
-> systemctl restart containerd
```
2. kubelet is not running
```ubuntu
[kubelet-check] Initial timeout of 40s passed.
-> kubeadm reset
-> sudo apt-get purge kubeadm kubectl kubelet kubernetes-cni kube* # 패키지와 관련된 설정을 제거
-> sudo apt-get autoremove # 의존성이 없는 패키지 제거
-> rm -rf ~/.kube # 클러스터 설정 파일 제거
-> sudo apt install kubeadm kubelet kubectl kubernetes-cni # 패키지 재설치
```

## 3.2 클러스터 시작하기
클러스터를 시작하기 위해 다음 명령어를 실행한다.  
이 명령어는 kubeadm init 명령어를 통해 생성된 명령어를 출력한다.
```ubuntu
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config # 현재 사용자로 kubeconfig 파일의 소유권을 변경

kubectl get pods --all-namespaces # 파드의 상태를 확인
kubectl get nodes # 노드의 상태를 확인
```
결과를 보면 마스터 노드의 호스트 이름은 사용자 $HOME 이며 control-plane과 master 역할을 하는것으로 표시된다.  
또한 coredns는 pending 상태이다.  
이 문제를 해결하려면 플라넬 CNI를 설치해야 한다.

## 3.3 네트워크 설정하기

### 3.3.1 플라넬 CNI 설치
```ubuntu
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

kubectl get pods --all-namespaces # 파드의 상태를 확인 -> CoreDNS가 Running 상태로 변경됨
```
반드시 CoreDns가 Running 상태로 변경되어야 한다. 실행 중이어야 파드들의 네트워크 통신이 가능하다.

# 4. 워커 노드 설치하기
마스터 노드에서 클러스터 생성시 출력해준 명령어를 워커 노드에서 실행한다.
```ubuntu
kubeadm token create --print-join-command # 마스터 노드에서 실행 토큰 재 생성
kubeadm join 198.19.249.75:6443 --token dzdleb.vwzlsduglb59471f \
	--discovery-token-ca-cert-hash sha256:0b7c75d3e342e6e6b8aa9bd5a460ac53ee1e29aed0c0ae2af282eadfc7750aa1
```
Run 'kubectl get nodes' on the control-plane to see this node join the cluster.  
라는 문구가 나타나면 정상적으로 설치된 것이다.  
마스터 노드에서 kubectl get nodes 명령어를 실행하면 워커 노드가 추가된 것을 확인할 수 있다.


# restart
```ubuntu
sudo kubeadm reset
sudo systemctl restart kubelet
```