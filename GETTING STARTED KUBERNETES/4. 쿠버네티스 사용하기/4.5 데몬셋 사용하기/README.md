# 데몬셋 사용하기
데몬셋은 모든 노드에 파드를 실행할 때 사용한다.  
레플리카셋이 특정 개수의 파드를 유지할 때 사용하는 것이라면, 데몬 셋은 모든 노드에 파드를 배포할 때 사용한다.  
그래서 모든 노드에 필요한 모니터링 용도로 많이 사용한다.

```bash
vi daemonset.yaml
```
```yaml
apiVersion: apps/v1
kind: DaemonSet # 데몬셋 배포
metadata:
  name: prometheus-daemonset
spec:
    selector:
        matchLabels:
        tier: monitoring # 사용자 정의 레이블로 모니터링용도로 사용될 것임을 지정
        name: prometheus-exporter
    template:
        metadata:
            labels:
              tier: monitoring # 모니터링 용도
              name: prometheus-exporter
        spec:
          containers:
            - name: prometheus
              image: prom/node-exporter
              ports:
              - containerPort: 80
```

```bash
kubectl apply -f daemonset.yaml
```

- kubectl get pod -o wide : 모든 리소스에 대한 상세 정보를 보여준다.
- kubectl describe 파드명/디플로이먼트명: 파드명/디플로이먼트명으로 지정된 오브젝트에 대한 상세 정보를 보여준다.
    
```bash
kubectl get pod -o wide
kubectl describe daemonset/prometheus-daemonset
```


## 데몬셋 삭제 
```bash
kubectl delete -f daemonsets.yaml
```
