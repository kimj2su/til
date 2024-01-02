# 쿠버네티스를 사용할 수 있는 환경

# 3.2.1 직접 설치해 사용하기
쿠버네티스를 직접설치하는 방법에는 두가지가 있다.
1. 미니큐브(또는 k3s, kind)
2. 서버 / 데스크톱에 직접 설치

## 1. 경량 버전의 쿠버네티스 설치하기
쿠버네티스는 마스터노드와 하나 이상의 워커 노드로 구성된다.

### (1) 미니큐브
미니큐브는 개인 컴퓨터에서 단일 노드의 쿠버네티스 클러스터를 구성하고 사용할 수 있는 환경을 제공한다.
추가 노드를 구성할 수 없다는 단점이 있다.

### (2) k3s
Rancher 사에서 개발한 쿠버네티스의 경량 버전이다.  
기능에 제약이 있어 주로 학습이나 소규모 환경에서 사용한다.  
자원을 적게 사용하는 특성 때문에 엣지나 사물인터넷 환경에서 사용하면 좋다.

### (3) kind(Kubernetes in Docker)
도커 컨테이너 안에서 쿠버네티스 클러스터를 구성하는 방법이다.  
다중 노드를 구성할 수 있다.

## 2. 서버 / 데스크톱에 직접 설치하기

### (1) 가상화 툴을 이용해 설치하기
VMWare, VirtualBox, Hyper-V 등을 이용해 가상 머신을 생성하고, 가상 머신에서 쿠버네티스를 설치한다.

### (2) 가상화 툴 없이 직접 설치하기
쿠버네티스 특성상 마스터와 워커 노드가 필요하므로 두 대 이상을 구매해야 한다.  
서버와 데스크톱에 직접 설치할 때는 kubeadm을 이용해 설치한다.  
kubeadm은 쿠버네티스에서 제공하는 기본적인 도구로 클러스를 구축할 때 사용한다.  
일반적으로 기업에서 많이 사용하는 방법이기도 한다.


# 3.2.2 클라우드에서 사용하기
퍼블릭 클라우드 서비스 제공 업체들이 PaaS 형식의 서비스로 출시했다.  
1. AWS - EKS(Elastic Kubernetes Service)
2. Azure - AKS(Azure Kubernetes Service)
3. GCP - GKE(Google Kubernetes Engine)

### AWS EKS 
[EKS 설치 방법](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/getting-started.html)

### Azure AKS 
[AKS CLI 방법](https://docs.microsoft.com/ko-kr/azure/aks/kubernetes-walkthrough)  
[AKS Portal 방법](https://learn.microsoft.com/ko-kr/azure/aks/learn/quick-kubernetes-deploy-portal?tabs=azure-cli)

### GCP GKE
[GKE 방법](https://cloud.google.com/apigee/docs/hybrid/v1.2/install-create-cluster?hl=ko)
