# 컨피그맵과 시크릿
컨피그맵은 환경 변수 같은 값을 도커 이미지에 포함시키지 않고 별도로 분리해서 관리하는 방법을 제공한다.  
시크릿은 외부에 노출되어서는 안되는 정보들을 저장할 때 사용한다.

# 컨피그맵
```bash
kubectl create configmap <map-name> <data-source> <args>
```

- map-name: 컨피그맵 이름
- data-source: 컨피그맵에을 포함하는 파일 또는 디렉터리의 경로
- args: 컨피그맵을 생성하는 방법을 지정한다.
  - 파일로 생성: 파일/디렉터리(--from-file), 환경파일(--from-env-file)
  - 리터럴로 생성: 리터럴(--from-literal)

## 리터럴로 생성
```bash
kubectl create configmap <map-name> --from-file=[키]=[값]
kubectl create configmap my-config --from-literal+JAVA_HOME=/usr/java
```

### 변수 추가 지정
```bash
kubectl create configmap my-config --from-literal+JAVA_HOME=/usr/java --from-litetal=URL=http://localhost:8000
```

## 파일로 생성
```bash
echo Hello, World! >> configmap_test.html

kubectl create configmap configmap-firl --from-file configmap_test.html
```
환경 파일도 유사한 방법으로 사용하면 된다.
컨피그맵은 데이터를 저장하는 용도로 사용되기 때문에 파드에 볼륨을 마운트 하는 용도로 사용해도 좋다.


# 시크릿
시크릿은 비밀번호와 같은 민감한 정보들을 저장하는 용도로 사용한다.  
이런 정보들은 컨테이너에 저장하지 않고 실제 파드가 실행할 때 시크릿 값을 가져와서 파드에 제공한다.

```bash
kubectl create secret <secret-type> <secret-name> <data-source> <args>
kubectl create secret generic "Secret이름" --from-literal="Key1"="Value1" --from-literal="Key2"="Value2"

kubectl create secret generic dbuser --from-literal=username=testuser --from-literal=password=1234
```
