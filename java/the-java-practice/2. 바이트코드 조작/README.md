# 코드 커버리지는 어떻게 측정할까?
## Jacoco
- Java Code Coverage Tool
- 바이트 코드를 조작하여 코드 커버리지를 측정한다.


# 모자에서 토끼를 꺼내는 마술

```java
public class Moja {
    
    public String pullOut() {
        return "";
    }
}

public class Masulsa {
    public static void main(String[] args) {
        System.out.println(new Moja().pullOut());
    }
}
```
위의 코드에서 Moja 클래스를 수정하지 않고 Main 클래스의 출력 결과를 "Rabbit"이 되도록 코드를 작성하려면 어떻게 해야할까?  

## 바이트 코드 조작 라이브러리
- ASM
- Javassist
- ByteBuddy

## ByteBuddy
- ByteBuddy는 자바 바이트 코드를 생성하고 수정하는 데 사용할 수 있는 오픈 소스 자바 라이브러리이다.
```groovy
// https://mvnrepository.com/artifact/net.bytebuddy/byte-buddy
        implementation group: 'net.bytebuddy', name: 'byte-buddy', version: '1.14.11'
```

```java
public class Masulsa {
    public static void main(String[] args) {
        new ByteBuddy().redefine(Moja.class)
                .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                .make().saveIn(new File("/Users/kimjisu/Intellij/til/java/더 자바, 코드를 조작하는 다양한 방법/thejava/build/classes/java/main/"));
        //System.out.println(new Moja().pullOut());
    }
}
```

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example;

public class Moja {
    public Moja() {
    }

    public String pullOut() {
        return "Rabbit!";
    }
}
```
위와 같이 컴파일 파일에 바이트 코드가 수정되어 저장된다.


# javaagent
위의 코드를 보면 컴파일을 한 뒤에 실행을 해야하는데, 이를 자동으로 해주는 것이 javaagent이다.

```java
ClassLoader classLoader = Main.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);
        new ByteBuddy().redefine(
                        typePool.describe("com.example.Moja").resolve(),
                        ClassFileLocator.ForClassLoader.of(classLoader)
                )
                .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                .make().saveIn(new File("/Users/kimjisu/Intellij/til/java/더 자바, 코드를 조작하는 다양한 방법/thejava/build/classes/java/main/"));
```
위와 같이 스트링으로 클래스를 넘겨주면 클래스를 로딩하지 않아서 컴파일 시점에 바이트 코드를 조작할 수 있다.  
하지만 이렇게 하면 다른 곳에서 클래스를 로딩하면 의도한대로 동작하지 않을 수 있다.

## 1. MasulsaAgent 모듈 생성 및 빌드
```groovy
jar {
    manifest {
        attributes(
//                'Main-Class': 'com.example.TheJava',
                'Premain-Class': 'com.example.MasulsaAgent',
                'Can-Redefine-Classes': true,
                'Can-Retransform-Classes': true
        )
    }
}

public class MasulsaAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, typeDescription, classLoader, module, protectionDomain)
                        -> builder.method(ElementMatchers.named("pullOut")).intercept(FixedValue.value("Rabbit!")))
                .installOn(inst);
    }
}
```

## 2. Masulsa 모듈에 javaagent 설정
```java
public class Masulsa {
    public static void main(String[] args) {
        System.out.println(new Moja().pullOut());
    }
}
vmoptions: -javaagent:${path}/MasulsaAgent.jar
```

첫번째 방법은 컴파일 되는 파일 자체를 변경한다.  
이 방법은 파일시스템에 있는 컴파일된 파일을 변경하는것이 아니라 메모리에 있는 클래스를 변경한다.  
클래스 로더가 클래스를 읽어올 때 javaagent를 거쳐서 변경된 바이트코드를 읽어들여 사용한다.


# 바이트 코드 조작 툴 활용 예
## 프로그램 분석
- 코드에서 버그 찾는 툴
- 코드 복잡도 계산

## 클래스 파일 생성
- 프록시
- 특정 API 호출 접근 제한
- 스칼라 같은 언어의 컴파일러
그 밖에도 자바 소스 코드를 건드리지 않고 코드 변경이 필요한 여러 경우에 사용할 수 있다.
- 프로파일러 : 애플리케이션을 실행할 때 자바 에이전트로 특정한 프로파일러(메모리, 쓰레드 등등)를 붙여서 실행할 수 있다.
- 최적화
- 로깅

## 스프링이 컴포넌트 스캔을 하는 방법(ASM)
- 컴포넌트 스캔으로 빈 등록할 후보 클래스 정보를 찾는데 사용
- ClassPathScanningCandidateComponentProvider -> SimpleMetadataReader
- ClassReader와 Visitor 사용해서 클래스에 있는 메타 정보를 읽어온다.





