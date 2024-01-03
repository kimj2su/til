# yaml 파일 알아보기
오브젝트(디플로이먼트, 파드등)을 어떻게 배포할 것인지 정의할 필요가 있다.  
이때 쿠버네티스는 매니페스트라는 파일로 정의를 내린다.  

### 메니페스트
쿠버네티스의 오브젝트를 생성하기 위한 메타 정보를 yaml이나 json으로 기술한 파일을 말한다.

## yaml 파일 작성법
- 하나의 블록에 속하는 엔트리(entry)마다 - 를 붙인다. 여기서 엔트리는 키를 말한다.
- 키-값(key-value) 매핑은 ':''으로 구분한다.
- 문서의 시작과 끝네 '---'을 삽입할 수 있다. 대체로 여러 개의 오브젝트를 정의할 때 사용하고 하나의 리소스만 정의할 때는 사용하지 않는 경우도 있다.
- 키와 값 사이에 공백이 있어야한다.
- 주석은 '#'으로 처리한다.
- 들여 쓰기는 tab이 아닌 space로 한다.
- yaml 파일의 구조를 맵 혹은 디렉터리, 해시, 오브젝트라고도 함

```yaml
apiVersion: apps/v1   # 숫자, 불린(참, 거짓), 문자 사용 가능
kind: Job
metadata:
  name: job-test      # 하위 엔트리를 표시할때는 들여쓰기
spec:
  template:
    metadata:
      name: job-test
    spec:
      containers:
      - name: job-test # 리스트 형태의 값을 가질 때 '-'로 구분
        image: busybox
        command: ["echo", "job-test"] # 다중 값을 표현할 때 '[]'으로 표현
      restartPolicy: Never # 키 : restartPolicy, 값 : Never
```

[yaml 파일을 문법 확인 사이트 ](https://onlineyamltools.com/validate-yaml)
