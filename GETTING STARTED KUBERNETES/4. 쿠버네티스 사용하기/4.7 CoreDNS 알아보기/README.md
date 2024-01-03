# CoreDNS 
CoreDNS는 쿠버네티스에서 사용하는 DNS이다.  
CoreDNS는 클러스터를 지속적으로 모니터링하며 새로운 서비스나 파드가 생성되면 DNS를 업데이트합니다.

```bash
kubectl get po -n kube-system -l k8s-app=kube-dns
```
쿠버네티스 클러스터에서 CoreDNS로 사용되는 파드를 확인하는 명령어이다.  
kubeadm으로 설치하면 기본적으로 CoreDNS가 설치되어 있다.  


```bash
kubectl get svc -n kube-system -l k8s-app=kube-dns
```
CoreDNS는 두개의 파드로 구성되어 있다. 이 두개의 파드에 대한 서비스는 kube-system 네임스페이스에서 kube-dns라는 이름으로 실행된다.  
이 명령어는 kube-dns라는 이름으로 실행된 서비스를 확인하는 명령어이다.  

```bash
kubectl describe cm -n kube-system coredns

Name:         coredns
Namespace:    kube-system
Labels:       <none>
Annotations:  <none>

Data
====
Corefile:
----
.:53 {
    errors
    health {
       lameduck 5s
    }
    ready
    kubernetes cluster.local in-addr.arpa ip6.arpa {
       pods insecure
       fallthrough in-addr.arpa ip6.arpa
       ttl 30
    }
    prometheus :9153
    forward . /etc/resolv.conf {
       max_concurrent 1000
    }
    cache 30
    loop
    reload
    loadbalance
}


BinaryData
====

Events:  <none>
```
kubectel describe cm 명령어를 통해 CoreDNS정보를 담고 있는 coreFile 정보를 확인할 수 있습니다.

- errors: 에러가 발생하면 stdout에 그 내용을 기록한다.즉, 에러가 발생하면 사용자에게 그 내용을 출력해 알려주겠다는 의미이다.
- health: CoreDNS에 대한 상태를 확인하는 부분으로, http://localhost:8080/health를 통해 CoreDNS를 확인 가능하다.
- ready: DNS 서비스를 할 준비가 되었다면 200OK가 반환된다.
- kubernetes: CoreDNS가 쿠버네티스의 서비스 및 파드의 IP를 기반으로 DNS 질의에 응답을 한다.
- prometheus: 지정된 포드(9153)로 프로메테우스의 메트릭 정보를 확인할 수 있다.

## CoreDNS 동작 확인
```bash
kubectl run -it --rm \
busybox \
--image=busybox \
--restart=Never \
--cat /etc/resolv.conf
```

- nameserver: CoreDNS의 IP 주소를 나타낸다.
- search: 쿠버네티스 클러스터의 도메인을 나타낸다.
- ndots: 도메인에 접미사를 붙이지 않고 질의할 때 접미사를 붙일 횟수를 나타낸다.
