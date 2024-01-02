# 쿠너베티스 설치 환경

## (1) 운영 체제 요구 사항
- Ubuntu 16.04 LTS 이상
- Debian 9 이상
- CentOS 7 이상
- RHEL 7 이상
- Fedora 25 이상
- HypriotOS v1.0.1 이상
- Container Linux (알파)

## (2) 하드웨어 요구 사항
하드웨어의 경우 2개 이상의 CPU로 구성된 환경에서만 쿠버네티스를 설치할 수 있다.
- Master Node
  - CPU: 2개 이상의 CPU
  - Memory: 2GB 이상
  - Disk: 20GB 이상
    
| 구분   | 항목  | 요구 사항                                                                                                    |
|------|-----|----------------------------------------------------------------------------------------------------------|
| 하드웨어 | CPU | 2개 이상의 CPU                                                                                               |
|      | Memory | 2GB 이상                                                                                                   |
| 네트워크 | 고유한 MAC 주소 | 확인 방법: ifconfing -a 혹인 ip link                                                                           |
|      | 고유한 제조사 ID | 확인 방법: sudo cat /sys/class/dmi/id/product_uuid                                                           |
|      | 포트 오픈 | [마스터 노드] <br/>sudo firewall-cmd --permanent --add-port=6443(2379-2380, 10250, 10251,10252)/tcp           |
|      |      | [워커 노드] <br/>sudo firewall-cmd --permanent --add-port=10250(30000-32767)/tcp <br/> firewall-cmd --reload |

## 쿠버네티스에서 사용하는 포트
| 구분 | 프로토콜 | 포트        | 용도                              |
|-----|-------|-----------|---------------------------------|
| 마스터 노드 | TCP | 6443      | Kubernetes API 서버               |
| 마스터 노드 | TCP | 2379~2380 | etcd 서버 클라이언트 API               |
| 마스터 노드 | TCP | 10250     | kubelet API(모든 노드에서 접근 가능한 API) |
| 마스터 노드 | TCP | 10251     | kube-scheduler API                |
| 마스터 노드 | TCP | 10252     | kube-controller-manager API       |
| 워커 노드  | TCP | 10250     | kubelet API |
| 워커 노드  | TCP | 30000~32767 | NodePort 서비스                   |
