# 애노테이션 프로세서
# Lombok(롬복)은 어떻게 동작하는 걸까?
## Lombok
@Getter, @Setter, @Builder 등의 애노테이션과 애노테이션 프로세서를 제공하여 표준적으로 작성해야 할 코드를 개발자 대신 생성해주는 라이브러리.

## 롬복 사용하기
- 의존성 추가

```java
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```


- IntelliJ lombok 플러그인 설치
- IntelliJ Annotation Processing 옵션 활성화

# 롬복 동작 원리
컴파일 시점에 애노테이션 프로세서를 사용하여 소스코드의 AST(abstract syntax tree)를 조작한다.

# 논란 거리
- 공개된 API가 아닌 컴파일러 내부 클래스를 사용하여 기존 소스 코드를 조작한다.
- 특히 이클립스의 경우엔 java agent를 사용하여 컴파일러 클래스까지 조작하여 사용한다. 해당 클래스들 역시 공개된 API가 아니다보니 버전 호환성에 문제가 생길 수 있고 언제라도 그런 문제가 발생해도 이상하지 않다.
- 그럼에도 불구하고 엄청난 편리함 때문에 널리 쓰이고 있으며 대안이 몇가지 있지만 롬복의 모든 기능과 편의성을 대체하진 못하는 현실이다.
  - AutoValue
    - https://github.com/google/auto/blob/master/value/userguide/index.md
  - Immutables
  - https://immutables.github.io

# 애노테이션 프로세서
- 컴파일 시점에 소스코드의 AST(abstract syntax tree)를 조작할 수 있다.
- 자바6부터 지원

## Processor 인터페이스
- 여러 라운드에 거쳐 소스 컴파일 된 코드를 처리 할 수 있다.

## 유틸리티
- Javapoet : 소스 코드를 생성하는 라이브러리
- AutoService : META-INF/services/javax.annotation.processing.Processor 파일 생성, 서비스 프로바이더 레지스트리 생성기

# 애너테이션 프로세서 정리
애노테이션 프로세서 사용 예
- 롬복
- AutoService : java.util.ServiceLoader용 파일 생성 유틸리티
- @Override
- Dagger 2: 컴파일 타임 DI 제공
- 안드로이드 라이브러리
  - ButterKinfe: @BindView(뷰 아이디와 애노테이션 붙인 필드 바인디)
  - DeepLinkDisptch: 특정 URI 링크를 열었을 때 처리해야 하는 액티비티를 찾아주는 라이브러리

# 장점
- 런타임 비용이 제로 -> 컴파일 시점에 이미 다 끝남

# 단점
- 기존의 코드를 변경하는 퍼블릭한 API 방법이 없다.